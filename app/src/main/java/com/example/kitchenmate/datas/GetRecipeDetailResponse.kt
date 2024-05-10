package com.example.kitchenmate.datas

data class RecipeIngredient(
    val name: String,
    val amount: Int,
    val amountUnit: String
)

data class RecipeDetailItem(
    val name: String,
    val steps: ArrayList<String>,
    val imageUrl: String,
    val ingredients: ArrayList<RecipeIngredient>,
    val isBookmarked: Boolean,
)

data class GetRecipeDetailResponse(val status: Number, val recipeDetails:RecipeDetailItem,  val error: String)
