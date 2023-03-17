package com.example.eatup.model

class ParentModel {
    var buyDate: String? = null
    var itemNo : String? = null
    private var FoodModelList: ArrayList<FoodModel?>? = null

    constructor() {}
    constructor(buyDate: String?,itemNo:String?, FoodModelList: ArrayList<FoodModel?>?) {
        this.FoodModelList = FoodModelList
        this.buyDate = buyDate
        this.itemNo = itemNo
    }

    fun getFoodModelList(): ArrayList<FoodModel?>? {
        return FoodModelList
    }

    fun setFoodModelList(FoodModelList: ArrayList<FoodModel?>?) {
        this.FoodModelList = FoodModelList
        if (FoodModelList != null) {
            FoodModelList.removeAt(0)
        }
    }

}