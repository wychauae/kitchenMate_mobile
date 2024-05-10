package com.example.kitchenmate.repositories

import android.app.Application
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class RecipeRepository (private val consumer: APIConsumer, val application: Application) {
    fun getRecipeList() = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getRecipeList()
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

//    fun addBookmarkRecipe(body: String) = flow {
//        emit(RequestStatus.Waiting)
//        val response = consumer.addBookmarkRecipe("Bearer" + AuthToken.getInstance(application.baseContext).token!!.toString() , body)
//        if(response.isSuccessful){
//            emit(RequestStatus.Success(null))
//        }
//        else{
//            val errorBody = response.errorBody()?.string()
//            if (errorBody != null) {
//                val errorJson = JSONObject(errorBody)
//                val error = errorJson.getString("error")
//                emit(RequestStatus.Error(error))
//            } else {
//                emit(RequestStatus.Error("Unknown error, please try again"))
//            }
//        }
//    }


}