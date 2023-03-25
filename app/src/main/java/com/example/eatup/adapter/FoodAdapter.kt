package com.example.eatup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.FoodAdapter.FoodviewHolder
import com.example.eatup.databinding.ActivityEachFoodBinding
import com.example.eatup.model.FoodWithInventory
import com.example.eatup.model.UserFoodWithInventory

class FoodAdapter(private val foodItemList: List<UserFoodWithInventory>) :
    RecyclerView.Adapter<FoodviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodviewHolder {
        val item_RV = ActivityEachFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodviewHolder(item_RV)
    }

    override fun onBindViewHolder(holder: FoodviewHolder, position: Int) {
        holder.binding.foodText.text = foodItemList[position].foodItem.foodName
        holder.binding.expiryDateText.text = foodItemList[position].foodItem.expiryDate
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }
    inner class FoodviewHolder(val binding: ActivityEachFoodBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )

}