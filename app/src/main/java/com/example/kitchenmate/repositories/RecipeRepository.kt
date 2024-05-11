package com.example.kitchenmate.repositories

import android.app.Application
import android.util.Log
import com.example.kitchenmate.datas.AddBookmarkRequest
import com.example.kitchenmate.datas.EditRecipeRequest
import com.example.kitchenmate.datas.GetRecipeDetailRequest
import com.example.kitchenmate.datas.InsertRecipeRequest
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class RecipeRepository (private val consumer: APIConsumer, val application: Application) {
    fun getRecipeList(searchText: String?) = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getRecipeList(searchText)
        if(response.isSuccessful){
            emit(RequestStatus.Success(response.body()))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }
    fun getRecipeDetail(username:String, id:String) = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getRecipeDetails(username, id)
        if(response.isSuccessful){
            emit(RequestStatus.Success(response.body()))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }

    fun createRecipe(body: InsertRecipeRequest) = flow{
        emit(RequestStatus.Waiting)
        Log.d("createRecipe", "here")
        val response = consumer.insertRecipe("Bearer " + AuthToken.getInstance(application.baseContext).token!!, body)
        Log.d("createRecipe", body.toString())
        if(response.isSuccessful){
            emit(RequestStatus.Success(response.body()))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }


    fun addBookmarkRecipe(body: AddBookmarkRequest) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.addBookmarkRecipe("Bearer " + AuthToken.getInstance(application.baseContext).token!!, body)
        if(response.isSuccessful){
            emit(RequestStatus.Success(null))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }

    fun removeBookmarkRecipe(id: String) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.removeBookmarkRecipe("Bearer " + AuthToken.getInstance(application.baseContext).token!!, id)
        if(response.isSuccessful){
            emit(RequestStatus.Success(null))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }
    fun editRecipe(body: EditRecipeRequest) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.editRecipe("Bearer " + AuthToken.getInstance(application.baseContext).token!!, body)
        if(response.isSuccessful){
            emit(RequestStatus.Success(null))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }



}