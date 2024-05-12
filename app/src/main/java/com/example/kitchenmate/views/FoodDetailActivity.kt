package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.ActivityCreateFoodBinding
import com.example.kitchenmate.databinding.ActivityFoodDetailBinding
import com.example.kitchenmate.datas.FoodRecordLogItem
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModel
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModelFactory
import com.example.kitchenmate.viewModels.FoodDetailActivityViewModel
import com.example.kitchenmate.viewModels.FoodDetailActivityViewModelFactory
import com.example.kitchenmate.viewModels.LoginActivityViewModel
import com.example.kitchenmate.viewModels.LoginActivityViewModelFactory
import com.example.kitchenmate.viewModels.RegisterActivityViewModel
import com.example.kitchenmate.viewModels.RegisterActivityViewModelFactory

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFoodDetailBinding
    private lateinit var mViewModel: FoodDetailActivityViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        mBinding = ActivityFoodDetailBinding.inflate(LayoutInflater.from(this))
//        mViewModel = ViewModelProvider(this, DActivityViewModelFactory(RecipeRepository(APIService.getService(), application), application))[DetailActivityViewModel::class.java]
        val foodName = intent.getStringExtra("name")
        val foodPhoto = intent.getStringExtra("photo")
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")
        val amountUnit = intent.getStringExtra("amountUnit")
        val id = intent.getStringExtra("id")
        val tvName: TextView = findViewById <TextView>(R.id.tvName)
        val tvDescription: TextView = findViewById(R.id.tvDescription)
        val tvAmount: TextView = findViewById<TextView>(R.id.tvAmount)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val consumeButton = findViewById<Button>(R.id.btnConsume)
        val replenishButton = findViewById<Button>(R.id.btnRestock)
        val food_image = findViewById<ImageView>(R.id.foodImageView)
        mViewModel = ViewModelProvider(this, FoodDetailActivityViewModelFactory(FoodRepository(APIService.getService(), application), application))[FoodDetailActivityViewModel::class.java]

        tvName.text = foodName
        tvDescription.text = description
        tvAmount.text = amount+" "+amountUnit

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_food_record_log)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val foodRecordLogAdapter = FoodRecordLogAdapter(mutableListOf()) // Initialize with an empty list
        recyclerView.adapter = foodRecordLogAdapter
        mViewModel.getFoodRecordLog().observe(this, Observer { logItems ->
            if (logItems != null) {
                foodRecordLogAdapter.updateData(logItems)
            }
        })

        // Fetch data
        if (id != null) {
            mViewModel.fetchFoodRecordLog(id)
        }

        backButton.setOnClickListener {
            val it = Intent(this, HomeActivity::class.java)
            startActivity(it)
        }

        consumeButton.setOnClickListener{
            val intent = Intent(this, FoodRecordActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        replenishButton.setOnClickListener{
            val intent = Intent(this, InsertFoodRecordActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            startActivity(intent)
        }

    }


}



