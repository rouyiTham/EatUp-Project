package com.example.eatup.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.ItemListener
import com.example.eatup.R
import com.example.eatup.contributionPage
import com.example.eatup.model.FoodModel
import com.example.eatup.model.ParentModel
import com.example.eatup.model.foodItem
import com.example.eatup.parentItem

class FoodAdapter() : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var foodModelList: ArrayList<FoodModel?>? = null

    val itemListener: ItemListener? = null
    val arrayList_c = ArrayList<String>()


    fun setFoodModelList(foodModelList: ArrayList<FoodModel?>?) {
        if (foodModelList != null) {
            this.foodModelList = foodModelList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_nested_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodModel = foodModelList?.get(position)
        holder.foodName.text = foodModel?.foodName
        holder.expiryDate.text = foodModel?.expiryDate

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                foodModel?.foodName?.toString()?.let { it1 ->
                    arrayList_c.add(it1)
                }
            } else {
                foodModel?.foodName?.toString()?.let { it1 ->
                    arrayList_c.remove(it1)
                }
            }
        }


        //foodModelList?.toString()?.let { it1 -> arrayList_c.add(it1) }
          //  if(holder.checkBox.isChecked){ //when the checkbox is ticked , the items is saved into an arraylist
              //  foodModel?.foodName?.toString()?.let { it1 -> arrayList_c.add(it1) }
              //   foodModelList?.toString()?.let { it1 -> arrayList_c.add(it1) }
          //  }

           /* itemListener?.onItemChange(arrayList_c)
            if (itemListener != null) {
                ItemAdapter(this,arrayList_c,itemListener)
            }*/
        }


    override fun getItemCount(): Int {
        return if (foodModelList != null){
            foodModelList!!.size
        }else {
            0;
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView
        val expiryDate: TextView
        val checkBox : CheckBox


        init {
            foodName = itemView.findViewById(R.id.foodNameText)
            expiryDate = itemView.findViewById(R.id.expiryDayText)
            checkBox = itemView.findViewById(R.id.checkBox)

        }
    }
}
