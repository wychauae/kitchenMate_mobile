package com.example.kitchenmate.datas

import okhttp3.MultipartBody

data class InsertRecipeRequest(val name: String, val steps: ArrayList<String>, val ingredients: ArrayList<RecipeIngredient>, val username: String, val createType: String, val image: String,)
