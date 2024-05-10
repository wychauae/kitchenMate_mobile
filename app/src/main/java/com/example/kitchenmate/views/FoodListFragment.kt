package com.example.kitchenmate.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.FragmentFoodListBinding
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.FoodListFragmentViewModel
import com.example.kitchenmate.viewModels.FoodListFragmentViewModelFactory


class FoodListFragment : Fragment() {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private var foodList: List<FoodItem> = emptyList()

    private lateinit var mBinding: FragmentFoodListBinding
    private lateinit var mViewModel: FoodListFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFoodListBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this, FoodListFragmentViewModelFactory(FoodRepository(APIService.getService(), requireActivity().application), requireActivity().application))[FoodListFragmentViewModel::class.java]
        getFoodList()
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        foodRecyclerView = mBinding.foodRecyclerView
        foodRecyclerView.layoutManager = gridLayoutManager
        foodAdapter = FoodAdapter(foodList)
        foodRecyclerView.adapter = foodAdapter
        setUpObservers()
    }

    private fun getFoodList() {
        mViewModel.fetchFoodList()
        mViewModel.getFoodList().observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                foodList = it
                foodAdapter.updateFoodList(foodList)
            }
        }
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(viewLifecycleOwner){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getErrorMessage().observe(viewLifecycleOwner){
            if(it.contains("No token provided")){
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
    }
}