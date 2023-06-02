package com.example.eatup.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import androidx.room.util.TableInfo
import com.example.eatup.eachFood

data class InventoryWithFood(
    @Embedded val inventory : Inventory,
    @Relation(
        parentColumn = "receipt_Tag",
        entityColumn = "foodNo",
        associateBy = Junction(FoodInventoryRef::class)
    )
    val foodItem : List<FoodItem>
)

data class FoodWithInventory(
    @Embedded val foodItem: FoodItem,
    @Relation(
        parentColumn = "foodNo",
        entityColumn = "receipt_Tag",
        associateBy = Junction(FoodInventoryRef::class)
    )
    val inventory : List<Inventory>
)

data class UserFoodWithInventory(
    @Embedded val foodItem: FoodItem,
    @Relation(
        parentColumn = "foodNo",
        entityColumn = "user_id",
        associateBy = Junction(UserFoodInventoryRef::class)
    )
    val UserInventory: List<UserInventory>
)
