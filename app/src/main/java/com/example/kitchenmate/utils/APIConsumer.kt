package com.example.kitchenmate.utils

import com.example.kitchenmate.datas.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIConsumer {
    @POST("users/register")
    suspend fun registerUser(@Body body: RegisterUserRequest) : Response<RegisterUserResponse>

    @POST("users/login")
    suspend fun loginUser(@Body body: LoginUserRequest) : Response<LoginUserResponse>

    @GET("recipe/getRecipeDetails")
    suspend fun getRecipeDetail(@Body body: GetRecipeDetailRequest) : Response<GetRecipeDetailResponse>

    @GET("recipe")
    suspend fun getAllRecipe(@Body body: GetAllRecipeRequest) : Response<GetAllRecipeResponse>

}