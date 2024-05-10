package com.example.kitchenmate.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.FragmentBookMarkRecipeListBinding
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.BookmarkRecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.BookmarkRecipeListFragmentViewModel
import com.example.kitchenmate.viewModels.BookmarkRecipeListFragmentViewModelFactory

class BookMarkRecipeListFragment : Fragment() {

    private lateinit var bookmarkRecipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var bookmarkRecipeList: List<RecipeItem> = emptyList()

    private lateinit var mBinding: FragmentBookMarkRecipeListBinding
    private lateinit var mViewModel: BookmarkRecipeListFragmentViewModel

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
        recipeAdapter = RecipeAdapter(bookmarkRecipeList)
        bookmarkRecipeRecyclerView.adapter = recipeAdapter
        setUpObservers()
    }

    private fun getBookmarkRecipeList() {
        mViewModel.fetchBookmarkRecipeList()
        mViewModel.getBookmarkRecipeList().observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                bookmarkRecipeList = it
                recipeAdapter.updateRecipeList(bookmarkRecipeList)
            }
        }
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(viewLifecycleOwner){
            mBinding.progressBar.isVisible = it
        }
    }
}