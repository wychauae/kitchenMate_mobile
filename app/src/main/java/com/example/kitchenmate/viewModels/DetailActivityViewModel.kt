package com.example.kitchenmate.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.GetAllRecipeRequest
import com.example.kitchenmate.datas.GetRecipeDetailRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch
import kotlin.math.log

class DetailActivityViewModel(private val authRepository: AuthRepository, private val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    fun requestRecipeDetail(body: GetRecipeDetailRequest){
        viewModelScope.launch {
            authRepository.getRecipeDetail(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
//                        it.data.recipeName
//                        it.data.imageUrl
//                        it.data.steps
//                        it.data.ingredients
//                        return
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.error
                    }
                }
            }
        }
    }
    fun requestAllRecipe(body: GetAllRecipeRequest){
        viewModelScope.launch {
            authRepository.getAllRecipe(body).collect{
                when(it){
                    is RequestStatus.Waiting -> {
                        Log.d("waiting", "ture")
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
//                        Log.d("allRecipe", it.data.toString())
//                        it.data.allRecipe
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