package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class LoginActivityViewModel(private val authRepository: AuthRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isLoginCompleted: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = AuthToken.getInstance(application.baseContext).token != null }

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getIsLoginCompleted() = isLoginCompleted

    fun loginUser(body: LoginUserRequest){
        viewModelScope.launch {
            authRepository.loginUser(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
//                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
//                        isLoading.value = false
//                        isLoginCompleted.value = true
//                        AuthToken.getInstance(application.baseContext).token = it.data!!.accessToken
//                        AuthToken.getInstance(application.baseContext).username = it.data.username
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }
}