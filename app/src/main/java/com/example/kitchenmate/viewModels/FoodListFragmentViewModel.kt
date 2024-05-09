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
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var foodList: MutableLiveData<List<FoodItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getFoodList() = foodList

    fun fetchFoodList(){
        viewModelScope.launch {
            foodRepository.getFoodList().collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        foodList.value = it.data?.foodList
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