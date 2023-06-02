package com.example.eatup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.databinding.ActivityEachFoodBinding
import com.example.eatup.model.User
import com.example.eatup.model.UserFood
import com.example.eatup.model.WebDataItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.util.*

class ProductAdapter(private val productData: UserFood) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: ActivityEachFoodBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item_view = ActivityEachFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item_view)
    }

    override fun getItemCount(): Int {
        return productData.WebfoodItems.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.foodText.text = productData.WebfoodItems[position].product_name

        val df: DateTimeFormatter =
            DateTimeFormatterBuilder() // case insensitive to parse the date string
                .appendPattern("[dd/M/yyyy]")
                .appendPattern("[d/M/yyyy]")// create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH)
        val expiryDate: LocalDate = LocalDate.parse(productData.WebfoodItems[position].expiration_date ,df)
        val dateNow: LocalDate? = LocalDate.now()
        val expiredfood = dateNow?.until(expiryDate, ChronoUnit.DAYS).toString()

        if (dateNow != null) {
            holder.binding.expiryDateText.text =
                "Expiring in $expiredfood days"
            if(expiredfood <= 0.toString()){
                holder.binding.expiryDateText.text =
                    "Expired"
            }
        }
    }

}