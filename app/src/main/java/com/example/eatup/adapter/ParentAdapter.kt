package com.example.eatup.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.ItemListener
import com.example.eatup.R
import com.example.eatup.databinding.ActivityNestedItemBinding
import com.example.eatup.model.ParentModel
import com.example.eatup.parentItem

class ParentAdapter() : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var parentModelList: List<ParentModel>? = null
    val arrayList_b = ArrayList<String>()
    val itemListener: ItemListener? = null
    fun setParentModelList(parentModelList: List<ParentModel>) {
            this.parentModelList = parentModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_parent_item, parent, false)
        return ParentViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentModel = parentModelList?.get(position)

        holder.buyDate.text = parentModel?.buyDate
        holder.childRecyclerView.setHasFixedSize(true)

        //set the child recycler view based on the food Adapter
        val foodAdapter = FoodAdapter()
        foodAdapter.setFoodModelList(parentModel?.getFoodModelList())
        holder.childRecyclerView.adapter = foodAdapter

        //set the item count
        holder.itemNo.text = parentModel?.getFoodModelList()?.size.toString()
        foodAdapter.notifyDataSetChanged()


        holder.contributeBtn.setOnClickListener {
          for(i in foodAdapter.arrayList_c){
              arrayList_b.addAll(listOf(i))
              Log.d("TAG",arrayList_b.toString())
          }
        }

    }

    override fun getItemCount(): Int {
        if (parentModelList != null){
            return parentModelList!!.size;
        }else{
            return 0;
        }
    }

     //getting the date , child_RV , item textview //
    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buyDate: TextView
        val childRecyclerView: RecyclerView
        val itemNo : TextView
        val contributeBtn : Button

        init {
            buyDate = itemView.findViewById(R.id.dateText)
            childRecyclerView = itemView.findViewById(R.id.child_RV)
            itemNo = itemView.findViewById(R.id.itemNo)
            contributeBtn = itemView.findViewById(R.id.contributeBtn)
        }
    }
}