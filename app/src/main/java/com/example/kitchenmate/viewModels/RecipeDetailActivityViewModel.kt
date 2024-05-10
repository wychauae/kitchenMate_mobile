package com.example.kitchenmate.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.datas.GetRecipeDetailRequest
import com.example.kitchenmate.datas.RecipeDetailItem
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class RecipeDetailActivityViewModel(private val recipeRepository: RecipeRepository, private val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var recipeDetail: MutableLiveData<RecipeDetailItem>  = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getRecipeDetailItem() = recipeDetail


    fun getRecipeDetail(username:String, id:String){
        viewModelScope.launch {
            recipeRepository.getRecipeDetail(username, id).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        recipeDetail.value = it.data?.recipeDetails
                        Log.d("detail recipeID",
                            recipeDetail.value.toString()
                        )
                    }
                    is RequestStatus.Error -> {
                        isSuccess.value = false
                        isLoading.value = false
                        errorMessage.value = it.error
                        Log.d("detail error",it.error)
                    }
                }
            }
        }
    }
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