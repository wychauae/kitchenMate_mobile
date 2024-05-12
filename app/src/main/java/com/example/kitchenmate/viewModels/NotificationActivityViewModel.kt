package com.example.kitchenmate.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.ExpiredFoodRecordItem
import com.example.kitchenmate.repositories.FoodRecordRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class NotificationActivityViewModel (private val foodRecordRepository: FoodRecordRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var expiredFoodRecordList: MutableLiveData<List<ExpiredFoodRecordItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getErrorMessage() = errorMessage
    fun getExpiredFoodRecordList() = expiredFoodRecordList

    fun fetchExpiredFoodRecordList(){
        viewModelScope.launch {
            foodRecordRepository.getExpiredFoodRecordList().collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        expiredFoodRecordList.value = it.data?.expiredFoodList
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

    fun confirmExpired(id:String){
        viewModelScope.launch {
            foodRecordRepository.confirmExpired(id).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
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