package com.example.eatup

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.eatup.adapter.ContributeFoodAdapter
import com.example.eatup.adapter.FoodAdapter
import com.example.eatup.database.database
import com.example.eatup.databinding.ActivityFoodInventoryBinding
import com.example.eatup.model.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("StaticFieldLeak")
private lateinit var binding : ActivityFoodInventoryBinding
//private var contributeItemList = mutableListOf<UserFoodWithInventory>()
val foodItems = ArrayList<String>()
private var foodWithInventoryList  = mutableListOf<UserFoodWithInventory>()
var foodAdapter: FoodAdapter? = null
lateinit var imageMenu: ActionBarDrawerToggle
lateinit var navigationView: NavigationView
private lateinit var auth: FirebaseAuth
private lateinit var databaseReference: DatabaseReference

class FoodInventory : AppCompatActivity(){
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodInventoryBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.foodItemRv.layoutManager = LinearLayoutManager(this)
        foodAdapter = FoodAdapter(foodWithInventoryList)

        binding.foodItemRv.adapter = foodAdapter

        val drawerLayout: DrawerLayout = binding.drawerLayout

        imageMenu = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(imageMenu)
        imageMenu.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView = binding.navigationView

        //to navigate to each page
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
                R.id.scan -> {
                    val intent = Intent(this, scanPage::class.java)
                    startActivity(intent)
                }
                R.id.resources -> {
                    val intent = Intent(this, Resources::class.java)
                    startActivity(intent)
                }
                R.id.inventory -> {
                    val intent = Intent(this, FoodInventory::class.java)
                    //startActivity(intent)
                }
                R.id.account -> {
                    val intent = Intent(this, accountPage::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        //allow to select the menu items in menu.xml//
        fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (imageMenu.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }

        //Creating ArrayList//
        val userInventory: List<UserInventory> =
            arrayListOf(UserInventory(Firebase.auth.currentUser!!.uid, 1))

        val inventory: List<Inventory> = arrayListOf(
            Inventory(101, "04-04-2020"),
            Inventory(102, "03-02-2020")
        )

        val foodItem: List<FoodItem> = arrayListOf(
            FoodItem(1, "Kimchi", "29-03-2023"),
            FoodItem(2, "Tuna", "01-04-2023"),
            FoodItem(3, "Sardine", "31-03-2023")
        )

        val ref: List<FoodInventoryRef> = arrayListOf(
            FoodInventoryRef(101, 1),
            FoodInventoryRef(101, 2),
            FoodInventoryRef(102, 3)
        )

        val ref2: List<UserFoodInventoryRef> = arrayListOf(
            UserFoodInventoryRef(Firebase.auth.currentUser!!.uid, 1),
            UserFoodInventoryRef(Firebase.auth.currentUser!!.uid, 2),
            UserFoodInventoryRef(Firebase.auth.currentUser!!.uid, 3)
        )

        val db = database(this)

        lifecycleScope.launch {
            db.detailDao().insertFoodItem(foodItem)
            db.detailDao().insertInventory(inventory)
            db.detailDao().insertUser(userInventory)
            db.detailDao().insertFoodInventoryRef(ref)
            db.detailDao().insertUserFoodRef(ref2)
        }

        //fooditemList = db.detailDao().getFoodItemWithInventory() as MutableList<FoodWithInventory>

        // val sp = db.detailDao().getFoodItemWithInventory()
        val st = db.detailDao().getUserFoodItem()

        //This should change to only auto add//
        if (foodWithInventoryList.isEmpty()) {
            foodWithInventoryList.addAll(st)
            foodAdapter!!.notifyDataSetChanged()
           /* val df: DateTimeFormatter =
                DateTimeFormatterBuilder() // case insensitive to parse JAN and FEB
                    .appendPattern("dd-MM-yyyy") // create formatter (use English Locale to parse month names)
                    .toFormatter(Locale.ENGLISH)
            val expiryDate : LocalDate = LocalDate.parse(foodWithInventoryList.toString(),df)
            val dateNow : LocalDate? = LocalDate.now()
            if (dateNow != null) {
                if (dateNow.until(expiryDate, ChronoUnit.DAYS).equals(0) ){
                    foodWithInventoryList.removeAll(foodWithInventoryList)
                }
            }*/
        }

        //foodWithInventoryList = db.detailDao().getFoodItemWithInventory() as MutableList<FoodWithInventory>

        //SWIPE GESTURE//
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition

                    try {
                        when (direction) {
                            //if swipe left//
                            ItemTouchHelper.LEFT -> {
                                val deleteData = foodWithInventoryList[position]
                                foodWithInventoryList.removeAt(position)

                                //It should then delete from the ROOM dbs//
                                ///////////////////////////////////////////
                                foodAdapter!!.notifyDataSetChanged()
                                Snackbar.make(
                                    binding.foodItemRv,
                                    deleteData.foodItem.foodName,
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("Undo") {
                                        foodWithInventoryList.add(position, deleteData)
                                        //arrayList.add(position,deleteData);
                                        foodAdapter!!.notifyDataSetChanged()
                                    }.show()
                            }

                            ItemTouchHelper.RIGHT -> {
                                val contributeData = foodWithInventoryList[position]
                                foodWithInventoryList.removeAt(position)

                                auth = Firebase.auth
                                val uid = auth.currentUser?.uid

                                foodItems.add(contributeData.foodItem.foodName)
                                Log.i("TAG", foodItems.toString())
                                val contribution = ContributeFoodItems(Firebase.auth.currentUser!!.uid,
                                    foodItems)

                                lifecycleScope.launch {
                                    database(this@FoodInventory).detailDao()
                                        .insertContributeItems(contribution)
                                }

                                binding.contributeBtn.setOnClickListener {
                                    foodItems.removeAll(foodItems.toSet())
                                    val intent = Intent(this@FoodInventory, contributionPage::class.java)
                                    startActivity(intent)

                                }


                                /*databaseReference =
                                    FirebaseDatabase.getInstance("https://learned-skill-377010-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("NGO")

                                if (uid != null) {
                                        databaseReference.child(uid)
                                            .child("FoodItems").setValue(c_user)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(
                                                        baseContext,
                                                        "Success",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        baseContext,
                                                        "Failed",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                }*/

                                foodAdapter!!.notifyDataSetChanged()
                                Snackbar.make(
                                    binding.foodItemRv,
                                    contributeData.foodItem.foodName,
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("Undo") {
                                        foodWithInventoryList.add(position, contributeData)
                                        //arrayList.add(position,deleteData);
                                        foodAdapter!!.notifyDataSetChanged()
                                    }.show()
                            }
                        }

                    } catch (e: java.lang.Exception) {
                        Toast.makeText(this@FoodInventory, e.message, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                this@FoodInventory,
                                R.color.purple_200
                            )
                        )
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(this@FoodInventory, R.color.purple_200)
                        )
                        .addSwipeLeftActionIcon(R.drawable.baseline_delete_forever_24)
                        .addSwipeLeftLabel("Delete")
                        .addSwipeLeftActionIcon(R.drawable.baseline_add_task_24)
                        .addSwipeRightLabel("Contribute")
                        .setSwipeLeftLabelColor(
                            ContextCompat.getColor(
                                this@FoodInventory,
                                R.color.white
                            )
                        )
                        .setSwipeRightLabelColor(
                            ContextCompat.getColor(this@FoodInventory, R.color.white)
                        )
                        .create()
                        .decorate()
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.foodItemRv)
    }
}
    /*fun getItems(): MutableList<UserFoodWithInventory> {
        return contributeItemList
    }*/
 /*   companion object {
        @SuppressLint("StaticFieldLeak")
        var getMyContext:Context?= null
            private set
    }

    override fun addItems(List: List<UserFoodWithInventory>) {
        contributeItemList.addAll(contributeItemList)
    }

    override val items: List<UserFoodWithInventory>
        get() = contributeItemList
}*/

