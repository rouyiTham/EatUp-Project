package com.example.eatup.database

import androidx.room.*
import com.example.eatup.model.*

@Dao
interface dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodItem(st:List<FoodItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInventory(ap:List<Inventory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(ap:List<UserInventory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodInventoryRef(ref:List<FoodInventoryRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFoodRef(ref2 :List<UserFoodInventoryRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContributeItems(contribution : ContributeFoodItems)

    @Insert
    fun insertNGOdata(NGOdata : NGOdata)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductData(ap: List<WebDataItem>?)

    /*@Transaction
    @Query("SELECT * FROM NGOdata")
    fun getAllNGOdata(): NGOdata*/

    @Transaction
    @Query("SELECT * FROM ContributeFoodItems")
    fun getAllContribution(): ContributeFoodItems

    @Query("DELETE FROM ContributeFoodItems")
    fun deleteAllFoodItem()

    @Delete
    fun deleteEachFood(webDataItem:WebDataItem?)

    /*@Query()
    fun deleteEachFood(st :List<FoodItem>)*/


    /*this func wont work becuz userfoodwithinvetory is either an entity or a collection of array
    @Delete
    fun deleteFoodItem(userFoodWithInventory: UserFoodWithInventory)*/

    @Transaction
    @Query("SELECT * FROM Inventory")
    fun getInventory():List<Inventory>

    @Transaction
    @Query("SELECT * FROM FoodItem")
    fun getFoodItem():List<FoodItem>

    @Transaction
    @Query("SELECT * FROM Inventory")
    fun getInventoryWithFoodItem():List<InventoryWithFood>

    @Transaction
    @Query("SELECT * FROM FoodItem")
    fun getFoodItemWithInventory():List<FoodWithInventory>

    @Transaction
    @Query("SELECT * FROM FoodItem")
    fun getUserFoodItem(): List<UserFoodWithInventory>

    @Transaction
    @Query("SELECT * FROM ProductItems")
    fun getProductData(): List<WebDataItem>


}
