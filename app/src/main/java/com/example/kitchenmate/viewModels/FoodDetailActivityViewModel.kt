package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.FoodRecordLogItem
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class FoodDetailActivityViewModel (private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var foodRecordLog: MutableLiveData<List<FoodRecordLogItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getErrorMessage() = errorMessage
    fun getFoodRecordLog() = foodRecordLog

    fun fetchFoodRecordLog(foodId:String){
        viewModelScope.launch {
            foodRepository.getFoodRecordLogList(foodId).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        foodRecordLog.value = it.data
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isSuccess.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }
}