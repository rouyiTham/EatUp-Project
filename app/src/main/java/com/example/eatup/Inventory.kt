package com.example.eatup
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatup.adapter.ParentAdapter
import com.example.eatup.databinding.ActivityInventoryBinding
import com.example.eatup.databinding.ActivityNestedItemBinding
import com.example.eatup.model.Contribute
import com.example.eatup.model.ParentModel
import com.example.eatup.viewmodel.FirebaseViewModel

class Inventory: AppCompatActivity() {
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var parentAdapter: ParentAdapter
    private lateinit var itemListener: ItemListener
    private lateinit var binding : ActivityNestedItemBinding
    private lateinit var contributeBtn : Button
    val arrayList_c = ArrayList<String>()


    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        parentRecyclerView = findViewById(R.id.main_RV)

        parentRecyclerView.setHasFixedSize(true)
        parentRecyclerView.layoutManager = LinearLayoutManager(this)

        val parentAdapter = ParentAdapter()
        parentRecyclerView.adapter = parentAdapter

        firebaseViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        firebaseViewModel.allData
        firebaseViewModel.parentModelMutableLiveData.observe(this) { parentModelList ->
            if (parentModelList != null) {
                parentAdapter.setParentModelList(parentModelList)
            }
            parentAdapter.notifyDataSetChanged()
        }
        firebaseViewModel.databaseErrorMutableLiveData.observe(
            this,
            Observer { error ->
                Toast.makeText(
                    this@Inventory,
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            })


    }
    }



