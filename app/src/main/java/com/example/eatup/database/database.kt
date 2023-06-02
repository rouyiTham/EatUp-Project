package com.example.eatup.database

import android.app.Application
import androidx.room.*
import com.example.eatup.model.*
import com.example.eatup.scanPage

@Database(
    entities = [UserInventory::class,Inventory::class,FoodItem::class,FoodInventoryRef::class,UserFoodInventoryRef::class,ContributeFoodItems::class,NGOdata::class,WebDataItem::class,UserFood::class],
    version = 21,
    exportSchema = false
)
@TypeConverters(InventoryTypeConverter::class,UserFoodConverter::class,CfoodTypeConverter::class)

abstract class database:RoomDatabase(){
    abstract fun detailDao() : dao

    companion object{
        @Volatile private var instance : database?=null
        private val lock = Any()

        operator fun invoke(context: Application) = instance?: synchronized(lock){
            instance?:buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Application) = Room.databaseBuilder(
            context.applicationContext,
            database::class.java,
            "FoodInventories"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        }
}