package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.RegisterUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class RegisterActivityViewModel(val authRepository: AuthRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isRegisterCompleted: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getIsRegisterCompleted() = isRegisterCompleted

    fun registerUser(body: RegisterUserRequest){
        viewModelScope.launch {
            authRepository.registerUser(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isRegisterCompleted.value = false
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isRegisterCompleted.value = true
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isRegisterCompleted.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }

}