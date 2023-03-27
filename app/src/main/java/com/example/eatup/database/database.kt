package com.example.eatup.database

import android.content.Context
import androidx.room.*
import com.example.eatup.model.*

@Database(
    entities = [UserInventory::class,Inventory::class,FoodItem::class,FoodInventoryRef::class,UserFoodInventoryRef::class,ContributeFoodItems::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(InventoryTypeConverter::class)
abstract class database:RoomDatabase(){
    abstract fun detailDao() : dao

    companion object{
        @Volatile private var instance : database?=null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance?:buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            database::class.java,
            "FoodInventories"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        }
}