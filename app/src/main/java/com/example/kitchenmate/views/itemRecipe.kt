package com.example.kitchenmate.views

data class itemRecipe(
    var foodName: String,
    var foodPhoto: Int,
    var recipeID: String,
    var ingredient: List<itemIngredient>,
    var step: List<String>
)
