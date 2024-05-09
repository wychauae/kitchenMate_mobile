package com.example.kitchenmate.datas
data class GetRecipeDetailResponse(val status: Number, val recipeName:String, val imageUrl:String, val steps:List<String>, val ingredients:String,  val error: String)
