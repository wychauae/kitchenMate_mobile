package com.example.kitchenmate.utils

import com.example.kitchenmate.datas.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIConsumer {
    @POST("users/register")
    suspend fun registerUser(@Body body: RegisterUserRequest) : Response<RegisterUserResponse>

    @POST("users/login")
    suspend fun loginUser(@Body body: LoginUserRequest) : Response<LoginUserResponse>

    @GET("food/")
    suspend fun getFoodList(@Header("Authorization") authToken: String) : Response<GetFoodListResponse>

    @GET("recipe/")
    suspend fun getRecipeList() : Response<GetRecipeListResponse>
}