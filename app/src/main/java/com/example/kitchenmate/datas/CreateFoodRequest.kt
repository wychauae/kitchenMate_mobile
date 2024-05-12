package com.example.kitchenmate.datas

data class CreateFoodRequest(
    val name: String,
    val description: String?,
    val amountUnit: String,
    val tag: String,
    val imageUrl: String?
)