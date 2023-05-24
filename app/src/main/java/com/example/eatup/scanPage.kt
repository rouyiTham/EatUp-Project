package com.example.eatup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.eatup.database.database
import com.example.eatup.databinding.ActivityScanPageBinding
import com.example.eatup.repo.Repository
import com.example.eatup.viewmodel.MainViewModel
import com.example.eatup.viewmodel.MainViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.launch

class scanPage : AppCompatActivity() {


    lateinit var imageMenu: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView
    private lateinit var captureBtn:MaterialButton
    private lateinit var galleryBtn:MaterialButton
    private lateinit var imageView: ImageView
    private lateinit var scanBtn: MaterialButton
    private lateinit var textResult:TextView
    private lateinit var viewModel : MainViewModel
    private lateinit var testBtn : Button


    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101

        private const val TAG = "MAIN_TAG"
    }

    //arrays of permission required to pick image from camera//gallery
    private lateinit var cameraPermissions:Array<String>
    private lateinit var storagePermissions:Array<String>

    //uri of the image will be taken
    private var imageUri: Uri?=null

    private var barcodeScannerOptions:BarcodeScannerOptions?=null
    private var barcodeScanner:BarcodeScanner?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_page)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        imageMenu = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(imageMenu)
        imageMenu.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView = findViewById(R.id.navigationView)

        //to navigate to each page
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
                R.id.scan -> {
                    val intent = Intent(this,ActivityScanPageBinding::class.java)
                    //startActivity(intent)
                }
                R.id.resources -> {
                    val intent = Intent(this,Resources::class.java)
                    startActivity(intent)
                }
                R.id.inventory -> {
                    val intent = Intent(this,FoodInventory::class.java)
                    startActivity(intent)
                }
                R.id.account -> {
                    val intent = Intent(this,accountPage::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        //allow to select the menu items in menu.xml//
        fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(imageMenu.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }

        captureBtn = findViewById(R.id.captureBtn)
        galleryBtn = findViewById(R.id.galleryBtn)
        imageView = findViewById(R.id.imageView)
        scanBtn = findViewById(R.id.scanBtn)
        textResult = findViewById(R.id.textResult)
        testBtn = findViewById(R.id.testBtn)

        //init the arrays of permissions required to pick image from CAMERA/GALLERY
        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE) //// WRITE OR READ
        storagePermissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        barcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()

        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions!!)

        //when cameraBtn clicked , check permissions related to camera
        captureBtn.setOnClickListener{
            if(checkCameraPermission()){
                pickImageCamera()
            } else{
                requestCameraPermission()
            }
        }

        //when galleryBtn clicked , check permission related to gallery
        galleryBtn.setOnClickListener{
            if(checkStoragePermission() != null){
                pickImageGallery()
            } else {
                requestStoragePermission()
            }
        }

        scanBtn.setOnClickListener{
            if(imageUri == null){
                showToast("Choose image first")
            }
            else {
                detectResultFromImage()
            }
        }

        testBtn.setOnClickListener {
            val rawValueText: EditText = findViewById(R.id.rawValuetext)
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
            val db = database(application)
            viewModel.getRawValue(rawValueText.text.toString())
            viewModel.myResponse.observe(this, Observer {
                    response->
                if(response.isSuccessful) {
                    textResult.text = response.body().toString()
                    lifecycleScope.launch {
                       db.detailDao().insertProductData(response.body())
                    }
                }
            })
        }
    }

   //detect and scan the image with barcode//
    private fun detectResultFromImage() {
        Log.d(TAG,"detectResultFromImage: ")
       try{
           val inputImage = InputImage.fromFilePath(this,imageUri!!)
           val barcodeResult = barcodeScanner?.process(inputImage)
               ?.addOnSuccessListener { barcodes->
                   extractBarcodeQRCodeInfo(barcodes)

               }?.addOnFailureListener{e ->
                   Log.e(TAG,"detectResultFromImage",e)
                   showToast("Failed scanning due to ${e.message}")
               }
       }catch(e:java.lang.Exception){
           Log.e(TAG,"detectResultFromImage",e)
           showToast("Failed due to ${e.message}")
       }
    }

    //extract the barcode from the image//
    private fun extractBarcodeQRCodeInfo(barcodes:List<Barcode>){
        for(barcode in barcodes){
            val bound = barcode.boundingBox
            val corners = barcode.cornerPoints
            //val rawValue = barcode
            //Log.d(TAG,"extractBarcodeQRCodeInfo: rawValue: $rawValue")

                    val rawValue = Barcode.FORMAT_EAN_13

                    textResult.text = "\nrawValue : $rawValue"

                    val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
                    val db = database(application)
                    viewModel.getRawValue(rawValue.toString())
                    viewModel.myResponse.observe(this, Observer {
                            response->
                        if(response.isSuccessful) {
                            textResult.text = response.body().toString()
                            lifecycleScope.launch {
                                db.detailDao().insertProductData(response.body())
                            }
                        }
                    })

                    /*val repository = Repository()
                    val viewModelFactory = MainViewModelFactory(repository)
                    viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

                    viewModel.getRawValue(rawValue)
                    viewModel.myResponse.observe(this, Observer {
                        response->
                        if(response.isSuccessful){
                            Log.d("Response",response.body()?.product_id.toString())
                        }
                    })*/
                }

    }



    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            imageUri = data?.data
            Log.d(TAG, "galleryActivityResultLauncher: imageUri:$imageUri")
            imageView.setImageURI(imageUri)
        }
        else {
            showToast("Failed")
        }
    }

    private fun pickImageCamera(){
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE,"Sample Image")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Sample Image description")
        
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
        
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ){result->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Log.d(TAG,"cameraActivityResultLauncher: imageUri: $imageUri")
            imageView.setImageURI(imageUri)
        }
    }

    private fun checkStoragePermission(){

        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                requestStoragePermission()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
              //Toast.makeText(this@scanPage,"You have denied access",Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {

            }

        }).onSameThread().check()
    }


    private fun requestStoragePermission(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, STORAGE_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        val resultCamera = (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)
        val resultStorage = (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)

        return resultCamera && resultStorage
    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermissions, CAMERA_REQUEST_CODE)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    val cameraAccepted= grantResults[0]==PackageManager.PERMISSION_GRANTED
                            if(cameraAccepted){
                                pickImageCamera()
                            }
                    else{
                        showToast("Camera permission are required")
                    }
                }
            }

            STORAGE_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                   val storageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED

                       if(storageAccepted){
                           pickImageGallery()
                   } else {
                       showToast("Storage permission are required")
                   }
                }
            }
        }
    }
    private fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}