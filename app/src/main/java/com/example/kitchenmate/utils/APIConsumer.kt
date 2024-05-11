package com.example.kitchenmate.utils
import com.example.kitchenmate.datas.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIConsumer {
    @POST("users/register")
    suspend fun registerUser(@Body body: RegisterUserRequest): Response<RegisterUserResponse>

    @POST("users/login")
    suspend fun loginUser(@Body body: LoginUserRequest): Response<LoginUserResponse>

    @GET("food/")
    suspend fun getFoodList(@Header("Authorization") authToken: String, @Query("searchText") searchText: String?) : Response<GetFoodListResponse>

    @GET("recipe/")
    suspend fun getRecipeList(@Query("searchText") searchText: String?) : Response<GetRecipeListResponse>

    @GET("bookmarkRecipe/")
    suspend fun getBookmarkRecipeList(@Header("Authorization") authToken: String, @Query("searchText") searchText: String?) : Response<GetRecipeListResponse>

    @GET("recipe/getRecipeDetails")
   	 suspend fun getRecipeDetails(@Query("username") username: String, @Query("id") id: String): Response<GetRecipeDetailResponse>

    @POST("recipe/")
    suspend fun insertRecipe(@Header("Authorization") authToken: String, @Body body: InsertRecipeRequest): Response<StatusOnlyResponse>

    @POST("bookmarkRecipe/")
    suspend fun addBookmarkRecipe(@Header("Authorization") authToken: String, @Body body: AddBookmarkRequest): Response<StatusOnlyResponse>

    @DELETE("bookmarkRecipe/")
    suspend fun removeBookmarkRecipe(@Header("Authorization") authToken: String, @Query("id") id: String): Response<StatusOnlyResponse>

    @PUT("recipe/")
    suspend fun editRecipe(@Header("Authorization") authToken: String, @Body body: EditRecipeRequest): Response<StatusOnlyResponse>


    @GET("recipe/compare/{id}")
    suspend fun compare(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): Response<CompareIngredientResponse>

    @GET("recipe/getRecipeListByUser")
    suspend fun getRecipeListByUser(@Header("Authorization") authToken: String): Response<GetRecipeListResponse>
} 