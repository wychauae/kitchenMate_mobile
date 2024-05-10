package com.example.kitchenmate.repositories

import android.app.Application
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class BookmarkRecipeRepository (private val consumer: APIConsumer, val application: Application) {
    fun getBookmarkRecipeList() = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getBookmarkRecipeList(
            "Bearer " + AuthToken.getInstance(application.baseContext).token!!)
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
}