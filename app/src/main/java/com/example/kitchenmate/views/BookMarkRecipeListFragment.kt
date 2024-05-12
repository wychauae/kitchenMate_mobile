package com.example.kitchenmate.views

import android.content.Intent
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
import com.example.kitchenmate.databinding.FragmentBookMarkRecipeListBinding
import com.example.kitchenmate.repositories.BookmarkRecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.BookmarkRecipeListFragmentViewModel
import com.example.kitchenmate.viewModels.BookmarkRecipeListFragmentViewModelFactory

class BookMarkRecipeListFragment : Fragment() {

    private lateinit var bookmarkRecipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    private lateinit var mBinding: FragmentBookMarkRecipeListBinding
    private lateinit var mViewModel: BookmarkRecipeListFragmentViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBookMarkRecipeListBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this, BookmarkRecipeListFragmentViewModelFactory(
            BookmarkRecipeRepository(
                APIService.getService(), requireActivity().application), requireActivity().application)
        )[BookmarkRecipeListFragmentViewModel::class.java]
        getBookmarkRecipeList()
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        bookmarkRecipeRecyclerView = mBinding.bookmarkRecipeRecyclerView
        bookmarkRecipeRecyclerView.layoutManager = gridLayoutManager
        recipeAdapter = RecipeAdapter("Bookmark", emptyList())
        bookmarkRecipeRecyclerView.adapter = recipeAdapter
        searchView = mBinding.bookmarkRecipeSearch
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
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(viewLifecycleOwner){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsSuccess().observe(viewLifecycleOwner){ it ->
            if(it) {
                mViewModel.getBookmarkRecipeList().observe(viewLifecycleOwner){
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
        mViewModel.getErrorMessage().observe(viewLifecycleOwner){
            if(it.contains("No token provided")){
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
    }

    private fun getBookmarkRecipeList() {
        mViewModel.fetchBookmarkRecipeList(null)
    }
    private fun searchList(searchText: String) {
        mViewModel.fetchBookmarkRecipeList(searchText)
    }
}