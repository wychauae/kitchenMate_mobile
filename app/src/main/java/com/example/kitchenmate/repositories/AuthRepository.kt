package com.example.kitchenmate.repositories

import android.util.Log
import com.example.kitchenmate.datas.RegisterUserRequest
import com.example.kitchenmate.datas.RegisterUserResponse
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class AuthRepository(private val consumer: APIConsumer) {
    private val TAG: String = "RegisterActivity"
    fun registerUser(body: RegisterUserRequest) = flow {
        emit(RequestStatus.Waiting)
        val response = consumer.registerUser(body)
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