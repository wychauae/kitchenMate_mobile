package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.repositories.FoodRepository

@Suppress("UNCHECKED_CAST")
class CreateFoodActivityViewModelFactory(private val foodRepository: FoodRepository, private val application: Application):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CreateFoodActivityViewModel::class.java) -> {
                CreateFoodActivityViewModel(foodRepository, application) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
            }
        }
    }
}