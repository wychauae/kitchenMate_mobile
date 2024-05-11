package com.example.kitchenmate.datas

data class EditRecipeItem(
    val name: String,
    val steps: ArrayList<String>,
    val ingredients: ArrayList<RecipeIngredient>,
    val username: String,
    val createType: String,
    val id: String,
    val imageUrl: String,
)

data class EditRecipeRequest(val recipeDetails:EditRecipeItem)
