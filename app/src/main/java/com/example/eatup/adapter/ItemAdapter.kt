package com.example.eatup.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.Inventory
import com.example.eatup.ItemListener
import com.example.eatup.R
import com.example.eatup.model.ParentModel
import com.example.eatup.repo.firebaserepo


class ItemAdapter(context: FoodAdapter, arrayList_c: ArrayList<String>, itemListener: ItemListener) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    private lateinit var itemListener: ItemListener
    private lateinit var parentModel: ParentModel
    private lateinit var firebaserepo: firebaserepo
    private lateinit var context : Context
    private lateinit var view : View
    private lateinit var inventory: Inventory

    var arrayList_c = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_contribution_page, parent, false)
        return ItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return arrayList_c.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox : CheckBox


        init {
            checkBox = itemView.findViewById(R.id.checkBox)


        }
    }
}