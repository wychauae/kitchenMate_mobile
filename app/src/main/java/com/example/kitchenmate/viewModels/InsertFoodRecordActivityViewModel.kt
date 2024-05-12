package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.CreateFoodRequest
import com.example.kitchenmate.datas.InsertFoodRecordRequest
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class InsertFoodRecordActivityViewModel(private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isInsertFoodRecordCompleted: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = AuthToken.getInstance(application.baseContext).token != null }

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getIsInsertFoodRecordCompleted() = isInsertFoodRecordCompleted

    fun insertFoodRecord(body: InsertFoodRecordRequest){
        viewModelScope.launch {
            foodRepository.insertFoodRecord(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isInsertFoodRecordCompleted.value = true
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isInsertFoodRecordCompleted.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }
}