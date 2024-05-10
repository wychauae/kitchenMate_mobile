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
import com.example.kitchenmate.databinding.FragmentRecipeListBinding
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.RecipeListFragmentViewModel
import com.example.kitchenmate.viewModels.RecipeListFragmentViewModelFactory

class RecipeListFragment : Fragment() {

    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: List<RecipeItem> = emptyList()

    private lateinit var mBinding: FragmentRecipeListBinding
    private lateinit var mViewModel: RecipeListFragmentViewModel

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
        recipeAdapter = RecipeAdapter(recipeList)
        recipeRecyclerView.adapter = recipeAdapter
        setUpObservers()
    }


    private fun getRecipeList() {
        mViewModel.fetchRecipeList()
        mViewModel.getRecipeList().observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                recipeList = it
                recipeAdapter.updateRecipeList(recipeList)
            }
        }
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(viewLifecycleOwner){
            mBinding.progressBar.isVisible = it
        }
    }
}