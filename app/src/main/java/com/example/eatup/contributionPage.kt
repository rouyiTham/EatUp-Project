package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.eatup.adapter.ContributeFoodAdapter
import com.example.eatup.adapter.FoodAdapter
import com.example.eatup.database.database
//import com.example.eatup.adapter.ContributeFoodAdapter
import com.example.eatup.databinding.ActivityContributionPageBinding
import com.example.eatup.model.ContributeFoodItems
import com.example.eatup.model.UserFoodWithInventory
import com.example.eatup.viewmodel.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


import java.util.Calendar

@SuppressLint("StaticFieldLeak")
private lateinit var binding : ActivityContributionPageBinding
private var contributeItemList = mutableListOf<ContributeFoodItems>()
//private var contributeFoodAdapter: ContributeFoodAdapter? = null
private lateinit var builder: AlertDialog.Builder
private lateinit var mapFragment: SupportMapFragment
private lateinit var mMap: GoogleMap

class contributionPage : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContributionPageBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        //binding.contributeItemRV.layoutManager = LinearLayoutManager(this)
        //contributeFoodAdapter = ContributeFoodAdapter(contributeItemList)
        //binding.contributeItemRV.adapter = contributeFoodAdapter

        val st = database(this).detailDao().getAllContribution()
        //contributeItemList.add(st)

        binding.foodText.text = st.foodItems.toString()
        // get reference to button
        findViewById<Button>(R.id.btnTimePicker)

        pickDate()

        //once finishBtn is clicked //
        val finishbutton: Button = findViewById(R.id.finishbutton)
        finishbutton.setOnClickListener {
            alert(view)
        }
    }

    fun onRadioButtonclicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioButton1 ->
                    if (checked) {
                        val authid = Firebase.auth.currentUser!!.uid
                        val databaseReference: DatabaseReference =
                            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                                .getReference("Users")
                        databaseReference.child(authid).get().addOnSuccessListener {
                            if (it.exists()) {
                                val address = it.child("Address").value
                                Log.d("TAG", address.toString())
                                //save address into NGO firebase//
                                ViewModel().insertAddress(address)
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                R.id.radioButton2 ->
                    if (checked) {
                        val databaseReference: DatabaseReference =
                            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                                .getReference("PickUpLocation")
                        databaseReference.get().addOnSuccessListener {
                            if (it.exists()) {
                                val address = it.child("Address").value
                                Log.d("TAG", address.toString())
                                //save address into NGO firebase//
                                ViewModel().insertAddress2(address)
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }


    }
    private fun alert(view: View) {
        builder = AlertDialog.Builder(this)

                val defaultlocationbtn: RadioButton = findViewById(R.id.radioButton1)
                val selectedlocationbtn: RadioButton = findViewById(R.id.radioButton2)

                if (defaultlocationbtn.isChecked && savedYear != 0 && savedMinute != 0 && savedHour != 0 && savedMonth != 0 && savedDay != 0 && savedYear != 0 && savedMinute != 0 && savedHour != 0 && savedMonth != 0 && savedDay != 0 || selectedlocationbtn.isChecked && savedYear != 0 && savedMinute != 0 && savedHour != 0 && savedMonth != 0 && savedDay != 0 && savedYear != 0 && savedMinute != 0 && savedHour != 0 && savedMonth != 0 && savedDay != 0) {
                    onRadioButtonclicked(view)
                    saveUserData()
                       builder.setTitle("Alert!")
                           .setMessage("The order will be picked up at the specified time and location! Thank you for your contribution!")
                           .setCancelable(true)//dialogbox in cancellable
                           .setPositiveButton("BACK") { dialogInterface, it ->
                               finish()
                           }
                           .show()
                } else {
                    Toast.makeText(this, "Please select location and time", Toast.LENGTH_SHORT)
                        .show()
                }
            }


    private fun saveUserData() {
        val authid = Firebase.auth.currentUser!!.uid
        val databaseReference =
            FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Users")
        databaseReference.child(authid).get().addOnSuccessListener {
            if (it.exists()) {
                val email = it.child("Profile").child("email").value
                ViewModel().insertEmail(email)
                val phone = it.child("Profile").child("phone").value
                ViewModel().insertPhone(phone)
            }
        }
    }


    /*fun findSelectionLocation() {
        var selectedLocationIndex = 0
        val pickuplocations = arrayOf("Sunway College, 2, Jalan Universiti, Bandar Sunway, 47500, Selangor","1, Jln Taylors, 47500 Subang Jaya, Selangor","Jalan Lagoon Selatan, Bandar Sunway, 47500 Subang Jaya, Selangor","1, Jalan Venna P5/2, Precinct 5, 62200 Putrajaya, Wilayah Persekutuan Putrajaya","50603 Kuala Lumpur, Federal Territory of Kuala Lumpur")

        var selectedLocation = pickuplocations[selectedLocationIndex]

        MaterialAlertDialogBuilder(this)
            .setTitle("Pick Up Locations")
            .setSingleChoiceItems(pickuplocations,selectedLocationIndex){dialog, which->
                selectedLocationIndex = which
                selectedLocation = pickuplocations[which]
            }
            .setPositiveButton("Ok") {dialog, which ->
                Toast.makeText(this,"$selectedLocation selected",Toast.LENGTH_SHORT).show()
                ViewModel().insertAddress(selectedLocation)
            }
            .setNeutralButton("Cancel"){dialog, which ->

            }
            .show()

    }*/

   /* private fun findDefaultLocation() {
        val authid = Firebase.auth.currentUser!!.uid
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
        databaseReference.child(authid).get().addOnSuccessListener {
            if(it.exists()){
                val address = it.child("Address").value
                Log.d("TAG",address.toString())
                //save address into NGO firebase//
                ViewModel().insertAddress(address)
            } else {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private fun getDateTimeCalendar(){
        val cal= Calendar.getInstance()
        day =cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        val btnTimePicker = findViewById<ImageButton>(R.id.btnTimePicker)

        btnTimePicker.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth= month
        savedYear= year

        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute= minute

        val textView = findViewById<TextView>(R.id.tv_textTime)
        textView.text="$savedDay-$savedMonth-$savedYear\nTime: $savedHour : $savedMinute"
        val datenTime = "$savedDay-$savedMonth-$savedYear\nTime: $savedHour : $savedMinute"

        val st = database(this).detailDao().getAllContribution()
        ViewModel().insertfood(st.foodItems.toString())
        ViewModel().inserttime(datenTime)
    }

}



