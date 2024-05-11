package com.example.kitchenmate.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.AddBookmarkRequest
import com.example.kitchenmate.datas.CompareItem
import com.example.kitchenmate.datas.EditRecipeRequest
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.datas.GetRecipeDetailRequest
import com.example.kitchenmate.datas.InsertRecipeItem
import com.example.kitchenmate.datas.InsertRecipeRequest
import com.example.kitchenmate.datas.RecipeDetailItem
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class RecipeDetailActivityViewModel(private val recipeRepository: RecipeRepository, private val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isCreateSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isAddBookmarkSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isRemoveBookmarkSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isEditRecipeSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isCompareSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var recipeDetail: MutableLiveData<RecipeDetailItem>  = MutableLiveData()
    private var compareItemList: MutableLiveData<List<CompareItem>>  = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getIsCreateSuccess() = isCreateSuccess
    fun getIsAddBookmarkSuccess() = isAddBookmarkSuccess
    fun getIsRemoveBookmarkSuccess() = isRemoveBookmarkSuccess
    fun getEditRecipeSuccess() = isEditRecipeSuccess
    fun getCompareSuccess() = isCompareSuccess
    fun getRecipeDetailItem() = recipeDetail
    fun getCompareItemList() = compareItemList


    fun getRecipeDetail(username:String, id:String){
        viewModelScope.launch {
            Log.d("call detail",
                "calling"
            )
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

    fun createRecipe(body: InsertRecipeRequest){
        viewModelScope.launch {
            Log.d("create recipe",body.toString() )
            recipeRepository.createRecipe(body).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isCreateSuccess.value = true
                        Log.d("create recipe",it.data.toString() )
                    }
                    is RequestStatus.Error -> {
                        isCreateSuccess.value = false
                        isLoading.value = false
                        errorMessage.value = it.error
                        Log.d("create error",it.error)
                    }
                }
            }
        }
    }
    fun addBookmarkRecipe(body: AddBookmarkRequest) {
        viewModelScope.launch {
            recipeRepository.addBookmarkRecipe(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        Log.d("waiting", "ture")
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isAddBookmarkSuccess.value = true
                        Log.d("addBookmarkRecipe recipe",it.data.toString() )
                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isAddBookmarkSuccess.value = false
                        errorMessage.value = it.error
                        Log.d("addBookmarkRecipe recipe",it.error.toString() )
                    }
                }
            }
        }
    }

    fun removeBookmarkRecipe(id: String) {
        viewModelScope.launch {
            recipeRepository.removeBookmarkRecipe(id).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        Log.d("waiting", "ture")
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isRemoveBookmarkSuccess.value = true
                        Log.d("removeBookmarkRecipe",it.data.toString() )
                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isRemoveBookmarkSuccess.value = false
                        errorMessage.value = it.error
                        Log.d("removeBookmarkRecipe",it.error.toString() )
                    }
                }
            }
        }
    }

    fun editRecipe(body: EditRecipeRequest) {
        viewModelScope.launch {
            recipeRepository.editRecipe(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        Log.d("waiting", "ture")
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isEditRecipeSuccess.value = true
                        Log.d("editRecipe success",it.data.toString() )
                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isEditRecipeSuccess.value = false
                        errorMessage.value = it.error
                        Log.d("editRecipe error",it.error.toString() )
                    }
                }
            }
        }
    }
    fun compare(id: String) {
        viewModelScope.launch {
            recipeRepository.compare(id).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        Log.d("waiting", "ture")
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isCompareSuccess.value = true
                        compareItemList.value = it.data?.ingredientsList
                        Log.d("compare",it.data.toString() )
                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        isCompareSuccess.value = false
                        errorMessage.value = it.error
                        Log.d("compare error",it.error.toString() )
                    }
                }
            }
        }
    }

}