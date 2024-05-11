package com.example.kitchenmate.datas

data class CompareItem(
    val _id: String,
    val name: String,
    val amount: Int,
    val amountUnit: String,
    val status: String,
)

data class CompareIngredientResponse(val status: Number, val ingredientsList:List<CompareItem>,  val error: String)
