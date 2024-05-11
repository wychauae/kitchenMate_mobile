package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.BookmarkRecipeRepository
import com.example.kitchenmate.utils.RequestStatus
import kotlinx.coroutines.launch

class BookmarkRecipeListFragmentViewModel (private val bookmarkRecipeRepository: BookmarkRecipeRepository, val application: Application): ViewModel() {

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var isSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var bookmarkRecipeList: MutableLiveData<List<RecipeItem>> = MutableLiveData()

    fun getIsLoading() = isLoading
    fun getIsSuccess() = isSuccess
    fun getErrorMessage() = errorMessage
    fun getBookmarkRecipeList() = bookmarkRecipeList

    fun fetchBookmarkRecipeList(searchText: String?){
        viewModelScope.launch {
            bookmarkRecipeRepository.getBookmarkRecipeList(searchText).collect{
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                        isSuccess.value = false
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isSuccess.value = true
                        bookmarkRecipeList.value = it.data?.bookmarkRecipeList
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