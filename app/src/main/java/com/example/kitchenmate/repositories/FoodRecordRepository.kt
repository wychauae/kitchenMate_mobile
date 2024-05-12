package com.example.kitchenmate.repositories

import android.app.Application
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class FoodRecordRepository (private val consumer: APIConsumer, val application: Application) {

    fun getExpiredFoodRecordList() = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.getExpiredFoodRecordList(
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

    fun confirmExpired(id: String) = flow{
        emit(RequestStatus.Waiting)
        val response = consumer.confirmExpired(
            "Bearer " + AuthToken.getInstance(application.baseContext).token!!, id)
        if(response.isSuccessful){
            emit(RequestStatus.Success(null))
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