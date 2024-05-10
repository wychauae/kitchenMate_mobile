package com.example.kitchenmate.datas

data class FoodItem(
    val _id: String,
    val name: String,
    val amount: String,
    val amountUnit: String,
    val description: String,
    val tag: String,
    val imageUrl: String
)

data class GetFoodListResponse(val status: Number, val foodList: List<FoodItem>, val error: String)

