package com.example.kitchenmate.datas

data class InsertRecipeItem(
    val name: String,
    val steps: ArrayList<String>,
    val ingredients: ArrayList<RecipeIngredient>,
    val username: String,
    val createType: String,
    val imageUrl: String,
)
//original is        val imageUrl: String,

data class InsertRecipeRequest(val recipeDetails:InsertRecipeItem)
