package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.CreateFoodRequest
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class CreateFoodActivityViewModel(private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isCreateFoodCompleted: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = AuthToken.getInstance(application.baseContext).token != null }

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getIsCreateFoodCompleted() = isCreateFoodCompleted

    fun createFood(body: CreateFoodRequest){
        viewModelScope.launch {
            foodRepository.createFood(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isCreateFoodCompleted.value = true
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isCreateFoodCompleted.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }
}