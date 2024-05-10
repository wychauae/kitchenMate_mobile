package com.example.kitchenmate.repositories

import android.app.Application
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.AuthToken

class FoodRepository (private val consumer: APIConsumer, val application: Application){

    fun getFoodList(searchText: String?) = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getFoodList(
            "Bearer " + AuthToken.getInstance(application.baseContext).token!!, searchText)
        if(response.isSuccessful){
            emit(RequestStatus.Success(response.body()))
        }
        else{
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = org.json.JSONObject(errorBody)
                val error = errorJson.getString("error")
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }
}