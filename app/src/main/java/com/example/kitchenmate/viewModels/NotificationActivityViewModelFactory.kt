package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.repositories.FoodRecordRepository

class NotificationActivityViewModelFactory (private val foodRecordRepository: FoodRecordRepository, private val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotificationActivityViewModel::class.java) -> {
                NotificationActivityViewModel(foodRecordRepository, application) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
            }
        }
    }
}