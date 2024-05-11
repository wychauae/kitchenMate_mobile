package com.example.kitchenmate.datas

data class RecipeItem(
    val _id: String,
    val name: String,
    val imageUrl: String
)

data class GetRecipeListResponse(val status: Number, val recipeList: List<RecipeItem>, val bookmarkRecipeList: List<RecipeItem>, val error: String)
