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
import com.example.kitchenmate.databinding.ActivityFoodRecordBinding
import com.example.kitchenmate.datas.FoodRecordItem
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
import com.example.kitchenmate.viewModels.FoodRecordActivityViewModel
import com.example.kitchenmate.viewModels.FoodRecordActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FoodRecordActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFoodRecordBinding
    private lateinit var mViewModel: FoodRecordActivityViewModel
    private lateinit var foodName: String
    private lateinit var description: String
    private lateinit var id: String
    private lateinit var amount: String
    private lateinit var amountUnit: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_record)

        mBinding = ActivityFoodRecordBinding.inflate(LayoutInflater.from(this))
        foodName = intent.getStringExtra("name").toString()
        description = intent.getStringExtra("description").toString()
        amount = intent.getStringExtra("amount").toString()
        imageUrl = intent.getStringExtra("imageUrl").toString()
        amountUnit = intent.getStringExtra("amountUnit").toString()
        id = intent.getStringExtra("id").toString()
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val tvName = findViewById<TextView>(R.id.tvName)
        tvName.text = foodName
        mViewModel = ViewModelProvider(this, FoodRecordActivityViewModelFactory(FoodRepository(APIService.getService(), application), application))[FoodRecordActivityViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id. recycler_view_food_record)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val foodRecordAdapter = FoodRecordAdapter(mutableListOf()) { item ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(item.expiredDate)
            val intent = Intent(this, DecreaseFoodRecordActivity::class.java).apply {
                putExtra("name", foodName)
                putExtra("description", description)
                putExtra("amount", amount)
                putExtra("amountUnit", amountUnit)
                putExtra("id", id)
                putExtra("imageUrl", imageUrl)
                putExtra("foodRecordId", item._id)
                putExtra("expiryDate", formattedDate)
                putExtra("remainingAmount", item.amount)
            }

            startActivity(intent)
        }
        recyclerView.adapter = foodRecordAdapter
        mViewModel.getFoodRecord().observe(this, Observer { recordItems ->
            if (recordItems != null) {
                foodRecordAdapter.updateData(recordItems)
            }
        })

        // Fetch data
        if (id != null) {
            mViewModel.fetchFoodRecord(id)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            intent.putExtra("imageUrl", imageUrl)
            startActivity(intent)
        }

    }


}



