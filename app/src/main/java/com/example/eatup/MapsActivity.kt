package com.example.eatup

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var currentLocation: Location? = null
    lateinit var userdb: DocumentReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()
    }

    fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
            return
        }
        val task = fusedLocationClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if (location != null) {
                this.currentLocation = location
                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    //override method for permission

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latlong = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        placeMarker(latlong)

        //location changes when screen is dragged//
        mMap?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(currentMarker!=null){
                    currentMarker?.remove()

                    val newLatLng = LatLng(p0?.position!!.latitude, p0?.position!!.longitude)
                    placeMarker(newLatLng)
                }
            }

            override fun onMarkerDragStart(p0: Marker) {
            }
        })
        }


    fun placeMarker(latLng: LatLng) {
            val markerOptions = MarkerOptions().position(latLng).title("Pick up here")
                .snippet(getAddress(latLng.latitude, latLng.longitude))

        //set new lat lng or location for the user

        mMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
        currentMarker = mMap?.addMarker(markerOptions)
        currentMarker?.showInfoWindow()
    }

    fun getAddress(lat: Double, lon: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(lat!!, lon!!, 1)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        userdb = FirebaseFirestore.getInstance().document("Users/UsersProfile")

        val updateMap: MutableMap<String, Any> = HashMap()
        updateMap["user-address"] = address.toString()

        val next: TextView= findViewById(R.id.next)
        next.setOnClickListener {
            val intent = Intent(this, defaultSetting::class.java)
            startActivity(intent)

            userdb.collection("Address").document(userId).set(updateMap).addOnSuccessListener {
                Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                    exception : java.lang.Exception -> Toast.makeText(this,exception.toString(),Toast.LENGTH_LONG).show()
            }
        }
        return address?.get(0)?.getAddressLine(0)
    }


    override fun onMarkerClick(p0: Marker) = false
}


















