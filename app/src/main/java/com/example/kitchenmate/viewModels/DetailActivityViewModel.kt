package com.example.kitchenmate.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.GetRecipeDetailRequest
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch
import kotlin.math.log

class DetailActivityViewModel(private val recipeRepository: RecipeRepository, private val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isSuccess: String = "false"
    private var recipeList: MutableLiveData<List<RecipeItem>> = MutableLiveData()

    fun getIsSuccess() = isSuccess
    fun getRecipeList() = recipeList

//    fun addBookmarkRecipe(body: String){
//        viewModelScope.launch {
//            recipeRepository.addBookmarkRecipe(body).collect{
//                when(it){
//                    is RequestStatus.Waiting -> {
//                        Log.d("waiting", "ture")
//                        isLoading.value = true
//                    }
//                    is RequestStatus.Success -> {
//                        isLoading.value = false
//                        isSuccess = "true"
//                    }
//                    is RequestStatus.Error -> {
//                        isLoading.value = false
//                        errorMessage.value = it.error
//                    }
//                }
//            }
//        }

}