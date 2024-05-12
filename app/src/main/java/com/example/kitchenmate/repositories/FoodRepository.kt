package com.example.kitchenmate.repositories

import android.app.Application
import android.util.Log
import com.example.kitchenmate.datas.CreateFoodRequest
import com.example.kitchenmate.datas.CreateFoodResponse
import com.example.kitchenmate.datas.DecreaseFoodRecordByFoodIdRequest
import com.example.kitchenmate.datas.InsertFoodRecordRequest
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.AuthToken
import org.json.JSONObject


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

    fun createFood(body: CreateFoodRequest) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.createFood("Bearer " + AuthToken.getInstance(application.baseContext).token!!,body)
        if (response.isSuccessful) {
            emit(RequestStatus.Success(null))
        } else {
            val errorBody = response.errorBody()?.string()

            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                Log.d("repo",error)
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }

    fun getFoodRecordLogList(foodId: String) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.getFoodRecordLog(
            "Bearer " + AuthToken.getInstance(application.baseContext).token!!, foodId)
        if (response.isSuccessful && response.body() != null) {
            emit(RequestStatus.Success(response.body()!!.foodRecordLogList))
            Log.d("repo",response.body()!!.foodRecordLogList.toString())
        } else {
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                Log.d("repo",error)
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }
    fun getFoodRecordList(foodId: String) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.getFoodRecord(
            "Bearer " + AuthToken.getInstance(application.baseContext).token!!, foodId)
        if (response.isSuccessful && response.body() != null) {
            emit(RequestStatus.Success(response.body()!!.foodRecordList))
            Log.d("repo",response.body()!!.foodRecordList.toString())
        } else {
            val errorBody = response.errorBody()?.string()
            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                Log.d("repo",error)
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }

    fun decreaseFoodRecord(body: DecreaseFoodRecordByFoodIdRequest, foodRecordId: String) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.decreaseFoodRecord("Bearer " + AuthToken.getInstance(application.baseContext).token!!, foodRecordId,body)
        if (response.isSuccessful) {
            emit(RequestStatus.Success(null))
            Log.d("repo",response.body().toString())
        } else {
            val errorBody = response.errorBody()?.string()

            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                Log.d("repo",error)
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }

    fun insertFoodRecord(body: InsertFoodRecordRequest) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.insertFoodRecord("Bearer " + AuthToken.getInstance(application.baseContext).token!!,body)
        if (response.isSuccessful) {
            emit(RequestStatus.Success(null))
        } else {
            val errorBody = response.errorBody()?.string()

            if (errorBody != null) {
                val errorJson = JSONObject(errorBody)
                val error = errorJson.getString("error")
                Log.d("repo",error)
                emit(RequestStatus.Error(error))
            } else {
                emit(RequestStatus.Error("Unknown error, please try again"))
            }
        }
    }
}