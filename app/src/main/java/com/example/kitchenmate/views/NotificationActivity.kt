package com.example.kitchenmate.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.ActivityNotificationBinding
import com.example.kitchenmate.repositories.FoodRecordRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.NotificationActivityViewModel
import com.example.kitchenmate.viewModels.NotificationActivityViewModelFactory

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: ExipiredFoodRecordAdapter

    private lateinit var mBinding: ActivityNotificationBinding
    private lateinit var mViewModel: NotificationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNotificationBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mViewModel = ViewModelProvider(this, NotificationActivityViewModelFactory(
            FoodRecordRepository(
                APIService.getService(), application), application)
        )[NotificationActivityViewModel::class.java]

        getExpiredFoodRecordList()

        val gridLayoutManager = GridLayoutManager(this, 1)
        notificationRecyclerView = mBinding.expiredFoodRecordRecyclerView
        notificationRecyclerView.layoutManager = gridLayoutManager
        notificationAdapter = ExipiredFoodRecordAdapter(emptyList(), mViewModel)
        notificationRecyclerView.adapter = notificationAdapter
        setUpObservers()
    }

    private fun setUpObservers(){
        mViewModel.getIsLoading().observe(this){
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsSuccess().observe(this){ it ->
            if(it) {
                mViewModel.getExpiredFoodRecordList().observe(this){
                    if(it.isNotEmpty()){
                        notificationAdapter.updateFoodRecordList(it)
                        mBinding.noResultFoundText.isVisible = false
                    } else {
                        notificationAdapter.updateFoodRecordList(it)
                        mBinding.noResultFoundText.isVisible = true
                    }
                }
            }
        }
        mViewModel.getErrorMessage().observe(this){
            if(it.contains("No token provided")){
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun getExpiredFoodRecordList() {
        mViewModel.fetchExpiredFoodRecordList()
    }
}