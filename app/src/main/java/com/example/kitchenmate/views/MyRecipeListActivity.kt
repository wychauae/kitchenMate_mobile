package com.example.kitchenmate.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.ActivityMyRecipeListBinding
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.MyRecipeListActivityViewModel
import com.example.kitchenmate.viewModels.MyRecipeListActivityViewModelFactory

class MyRecipeListActivity : AppCompatActivity() {

    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    private lateinit var mBinding: ActivityMyRecipeListBinding
    private lateinit var mViewModel: MyRecipeListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMyRecipeListBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this, MyRecipeListActivityViewModelFactory(
            RecipeRepository(
                APIService.getService(),application), application)
        )[MyRecipeListActivityViewModel::class.java]
        getRecipeListByUser()
        val gridLayoutManager = GridLayoutManager(this, 1)
        recipeRecyclerView = mBinding.recipeRecyclerView
        recipeRecyclerView.layoutManager = gridLayoutManager
        recipeAdapter = RecipeAdapter("Manage", emptyList())
        recipeRecyclerView.adapter = recipeAdapter
        setUpObservers()
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(this){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsSuccess().observe(this){ it ->
            if(it) {
                mViewModel.getMyRecipeList().observe(this){
                    if(it.isNotEmpty()){
                        recipeAdapter.updateRecipeList(it)
                        mBinding.noResultFoundText.isVisible = false
                    } else {
                        recipeAdapter.updateRecipeList(it)
                        mBinding.noResultFoundText.isVisible = true
                    }
                }
            }
        }
    }

    private fun getRecipeListByUser() {
        mViewModel.fetchRecipeListByUser()
    }
}