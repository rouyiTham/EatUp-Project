package com.example.eatup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eatup.model.*

@Database(
    entities = [UserInventory::class,Inventory::class,FoodItem::class,FoodInventoryRef::class,UserFoodInventoryRef::class],
    version = 3,
    exportSchema = false
)
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