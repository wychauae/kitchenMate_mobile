package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.FoodRecordItem
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class FoodRecordActivityViewModel (private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var foodRecord: MutableLiveData<List<FoodRecordItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getErrorMessage() = errorMessage
    fun getFoodRecord() = foodRecord

    fun fetchFoodRecord(foodId:String){
        viewModelScope.launch {
            foodRepository.getFoodRecordList(foodId).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        foodRecord.value = it.data
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