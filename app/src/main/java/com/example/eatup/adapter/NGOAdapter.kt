package com.example.eatup.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.R
import com.example.eatup.model.RetrievalData

class NGOAdapter(private val ngolist: ArrayList<RetrievalData>): RecyclerView.Adapter<NGOAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_each_ngo_alert,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = ngolist[position]

        holder.email.text = "Email : "+ currentItem.Email
        holder.phone.text = "Phone" + currentItem.Phone
        holder.time.text = "PickUpTime :" + currentItem.PickUpTime
        holder.address.text = "PickUpLoc: " + currentItem.Useraddress.toString().substring(22,100)
        holder.foodItems.text = "Food : " + currentItem.FoodToBeContributed

    }

    override fun getItemCount(): Int {
        return ngolist.size
    }

    /*fun updateNGOList(ngolist: List<RetrievalData>) {

        this.ngolist.clear()
        this.ngolist.addAll(ngolist)
        notifyDataSetChanged()

    }*/

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val email : TextView = itemView.findViewById(R.id.email)
        val phone : TextView = itemView.findViewById(R.id.phone_no)
        val time : TextView = itemView.findViewById(R.id.pick_up_time)
        val address : TextView = itemView.findViewById(R.id.pick_up_location)
        val foodItems : TextView = itemView.findViewById(R.id.item)
    }


}