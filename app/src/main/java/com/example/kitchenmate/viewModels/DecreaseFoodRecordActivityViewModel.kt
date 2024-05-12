package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.DecreaseFoodRecordByFoodIdRequest
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class DecreaseFoodRecordActivityViewModel(private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isDecreaseFoodRecordCompleted: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getIsDecreaseFoodRecordCompleted() = isDecreaseFoodRecordCompleted

    fun decreaseFoodRecord(body: DecreaseFoodRecordByFoodIdRequest, foodRecordId: String){
        viewModelScope.launch {
            foodRepository.decreaseFoodRecord(body,foodRecordId).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isDecreaseFoodRecordCompleted.value = false
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isDecreaseFoodRecordCompleted.value = true
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isDecreaseFoodRecordCompleted.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }

}