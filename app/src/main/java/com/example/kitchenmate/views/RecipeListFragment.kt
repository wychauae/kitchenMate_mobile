package com.example.kitchenmate.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.FragmentRecipeListBinding
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.RecipeListFragmentViewModel
import com.example.kitchenmate.viewModels.RecipeListFragmentViewModelFactory

class RecipeListFragment : Fragment() {

    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    private lateinit var mBinding: FragmentRecipeListBinding
    private lateinit var mViewModel: RecipeListFragmentViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this, RecipeListFragmentViewModelFactory(
            RecipeRepository(
                APIService.getService(), requireActivity().application), requireActivity().application)
        )[RecipeListFragmentViewModel::class.java]
        getRecipeList()
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        recipeRecyclerView = mBinding.recipeRecyclerView
        recipeRecyclerView.layoutManager = gridLayoutManager
        recipeAdapter = RecipeAdapter(emptyList())
        recipeRecyclerView.adapter = recipeAdapter
        searchView = mBinding.recipeSearch
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
        setUpObservers()
        setUpObservers()
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(viewLifecycleOwner){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsSuccess().observe(viewLifecycleOwner){ it ->
            if(it) {
                mViewModel.getRecipeList().observe(viewLifecycleOwner){
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
    private fun getRecipeList() {
        mViewModel.fetchRecipeList(null)
    }

    private fun searchList(searchText: String) {
        mViewModel.fetchRecipeList(searchText)
    }
}