package com.example.kitchenmate.repositories

import android.util.Log
import com.example.kitchenmate.datas.RegisterUserRequest
import com.example.kitchenmate.datas.RegisterUserResponse
import com.example.kitchenmate.utils.APIConsumer
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class AuthRepository(private val consumer: APIConsumer) {
    fun registerUser(body: RegisterUserRequest) = flow {
        emit(RequestStatus.Waiting)
        val response: Response<RegisterUserResponse> = consumer.registerUser(body)
        if(response.isSuccessful){
            emit(RequestStatus.Success(null))
        }
        else{
            emit(RequestStatus.Error(response.errorBody().toString()))
        }
    }
}