package com.example.kitchenmate.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.repositories.RecipeRepository

@Suppress("UNCHECKED_CAST")
class RecipeListFragmentViewModelFactory (private val recipeRepository: RecipeRepository, private val application: Application):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RecipeListFragmentViewModel::class.java) -> {
                RecipeListFragmentViewModel(recipeRepository, application) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
            }
        }
    }

}