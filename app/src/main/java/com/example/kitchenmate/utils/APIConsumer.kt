package com.example.kitchenmate.utils

import com.example.kitchenmate.datas.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

interface APIConsumer {
    @POST("users/register")
    suspend fun registerUser(@Body body: RegisterUserRequest) : Response<RegisterUserResponse>

    @POST("users/login")
    suspend fun loginUser(@Body body: LoginUserRequest) : Response<LoginUserResponse>

    @GET("food/")
    suspend fun getFoodList(@Header("Authorization") authToken: String, @Query("searchText") searchText: String?) : Response<GetFoodListResponse>

    @GET("recipe/")
    suspend fun getRecipeList(@Query("searchText") searchText: String?) : Response<GetRecipeListResponse>

    @GET("bookmarkRecipe/")
    suspend fun getBookmarkRecipeList(@Header("Authorization") authToken: String, @Query("searchText") searchText: String?) : Response<GetRecipeListResponse>

    @POST("food/")
    suspend fun createFood(@Header("Authorization") authToken: String, @Body body: CreateFoodRequest) : Response<CreateFoodResponse>

    @GET("foodrecord/{foodId}")
    suspend fun getFoodRecord(@Header("Authorization") authToken: String, @Path("foodId") foodId: String) : Response<GetFoodRecordByFoodIdResponse>

    @GET("foodrecordlog/{foodId}")
    suspend fun getFoodRecordLog(@Header("Authorization") authToken: String, @Path("foodId") foodId: String) : Response<GetFoodRecordLogByFoodIdResponse>

    @PATCH("foodrecord/{foodRecordId}")
    suspend fun decreaseFoodRecord(@Header("Authorization") authToken: String, @Path("foodRecordId") foodRecordId: String, @Body body: DecreaseFoodRecordByFoodIdRequest) : Response<DecreaseFoodRecordByFoodIdResponse>

    @POST("foodrecord/")
    suspend fun insertFoodRecord(@Header("Authorization") authToken: String, @Body body: InsertFoodRecordRequest) : Response<InsertFoodRecordResponse>
}