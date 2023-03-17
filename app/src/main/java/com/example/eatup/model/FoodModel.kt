package com.example.eatup.model

import org.threeten.bp.LocalDate
import org.w3c.dom.Text

class FoodModel {

    var foodName: String? = null
    var expiryDate: String? = null

    constructor() {}
    constructor(foodName: String?, expiryDate: String?) {
        this.foodName = foodName
        this.expiryDate = expiryDate

    }
}