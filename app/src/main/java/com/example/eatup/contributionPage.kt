package com.example.eatup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.FoodAdapter
import com.example.eatup.adapter.ItemAdapter

import java.util.Calendar

private lateinit var item_RV : RecyclerView
class contributionPage : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year =0
    var hour=0
    var minute=0

    var savedDay = 0
    var savedMonth = 0
    var savedYear =0
    var savedHour=0
    var savedMinute=0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contribution_page)
        // get reference to button
        findViewById<Button>(R.id.btnTimePicker)

        pickDate()

        item_RV = findViewById(R.id.item_RV)
        item_RV.setHasFixedSize(true)
        item_RV.layoutManager = LinearLayoutManager(this)

        val itemAdapter = FoodAdapter().itemListener?.let {
            ItemAdapter(
                FoodAdapter(), FoodAdapter().arrayList_c,
                it
            )
        }
        item_RV.adapter = itemAdapter

    }

    private fun getDateTimeCalendar(){
        val cal= Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
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


