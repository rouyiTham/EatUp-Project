package com.example.eatup.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.eatup.databinding.ActivityEachContributeItemBinding
import com.example.eatup.model.ContributeFoodItems
import com.example.eatup.model.UserFoodWithInventory

class ContributeFoodAdapter(private var contributionList: List<ContributeFoodItems>) :
    RecyclerView.Adapter<ContributeFoodAdapter.ContributeFoodviewHolder>() {

    /*init {
        contributeFoodItems = (FoodInventory.getMyContext as ContributeFoodItems).items
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributeFoodviewHolder {
        val contributeItem_RV = ActivityEachContributeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContributeFoodviewHolder(contributeItem_RV)
    }

    override fun onBindViewHolder(holder: ContributeFoodviewHolder, position: Int) {
        for(i in contributionList) {
            holder.binding.foodText.text = i.foodItems.toString()
        }
    }

    override fun getItemCount(): Int {
        return contributionList.size
    }

    inner class ContributeFoodviewHolder(val binding: ActivityEachContributeItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )


}