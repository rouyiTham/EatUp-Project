package com.example.eatup.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.FoodAdapter.FoodviewHolder
import com.example.eatup.databinding.ActivityEachFoodBinding
import com.example.eatup.model.UserFoodWithInventory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.util.*

class FoodAdapter(private val foodItemList: List<UserFoodWithInventory>) :
    RecyclerView.Adapter<FoodviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodviewHolder {
        val item_RV = ActivityEachFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodviewHolder(item_RV)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FoodviewHolder, position: Int) {
        holder.binding.foodText.text = foodItemList[position].foodItem.foodName

        val df: DateTimeFormatter =
            DateTimeFormatterBuilder() // case insensitive to parse JAN and FEB
                .appendPattern("dd-MM-yyyy") // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH)
        val expiryDate : LocalDate = LocalDate.parse(foodItemList[position].foodItem.expiryDate,df)
        val dateNow : LocalDate? = LocalDate.now()

        /*if (dateNow != null) {
            dateNow.until(expiryDate,ChronoUnit.DAYS)
        }*/

        if (dateNow != null) {
            holder.binding.expiryDateText.text = "Expiring in " + dateNow.until(expiryDate,ChronoUnit.DAYS).toString() + " days"
        }
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }
    inner class FoodviewHolder(val binding: ActivityEachFoodBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

}