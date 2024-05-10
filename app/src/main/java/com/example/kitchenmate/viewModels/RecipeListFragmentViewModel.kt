package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class RecipeListFragmentViewModel (private val recipeRepository: RecipeRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var recipeList: MutableLiveData<List<RecipeItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getErrorMessage() = errorMessage
    fun getRecipeList() = recipeList


    fun fetchRecipeList(){
        viewModelScope.launch {
            recipeRepository.getRecipeList().collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        recipeList.value = it.data?.recipeList
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