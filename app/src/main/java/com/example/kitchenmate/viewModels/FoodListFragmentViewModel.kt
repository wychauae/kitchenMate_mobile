package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class FoodListFragmentViewModel (private val foodRepository: FoodRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var foodList: MutableLiveData<List<FoodItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getErrorMessage() = errorMessage
    fun getFoodList() = foodList

    fun fetchFoodList(searchText:String?){
        viewModelScope.launch {
            foodRepository.getFoodList(searchText).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        foodList.value = it.data?.foodList
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