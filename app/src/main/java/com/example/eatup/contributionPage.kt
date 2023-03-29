package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
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
import com.example.eatup.model.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


import java.util.Calendar

@SuppressLint("StaticFieldLeak")
private lateinit var binding : ActivityContributionPageBinding
private var contributeItemList = mutableListOf<ContributeFoodItems>()
//private var contributeFoodAdapter: ContributeFoodAdapter? = null


class contributionPage : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour= 0
    var minute=0

    var savedDay = 0
    var savedMonth = 0
    var savedYear =0
    var savedHour= 0
    var savedMinute=0

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
        contributeItemList.add(st)

        binding.foodText.text = st.foodItems.toString()
        // get reference to button
        findViewById<Button>(R.id.btnTimePicker)

        pickDate()


        fun onRadioButtonClicked(view: View) {
            if (view is RadioButton) {
                // Is the button now checked?
                val checked = view.isChecked

                // Check which radio button was clicked
                when (view.getId()) {
                    R.id.radioButton1 ->
                        if (checked) {
                            findViewById<RadioButton>(R.id.radioButton1).setOnClickListener{
                                findDefaultLocation()
                            }
                        }
                    R.id.radioButton2 ->
                        if (checked) {
                            // select specified pick up location in the future
                        }
                }
            }
        }


        //once finishBtn is clicked //
        findViewById<Button>(R.id.finishbutton).setOnClickListener {
            database(this@contributionPage).detailDao().deleteAllFoodItem()
            contributeItemList.removeAll(listOf(st))
            val intent = Intent(this@contributionPage,FoodInventory::class.java)
            startActivity(intent)
        }

    }

    private fun findDefaultLocation() {
        val authid = Firebase.auth.currentUser!!.uid
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
        databaseReference.child(authid).get().addOnSuccessListener {
            if(it.exists()){
                val address = it.child("Address").value
                Log.d("TAG",address.toString())
                //save address into NGO firebase//

            } else {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

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


    }



}


