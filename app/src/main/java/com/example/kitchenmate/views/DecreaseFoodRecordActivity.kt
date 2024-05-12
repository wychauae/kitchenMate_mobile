package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.ActivityCreateFoodBinding
import com.example.kitchenmate.databinding.ActivityDecreaseFoodRecordBinding
import com.example.kitchenmate.databinding.ActivityFoodRecordBinding
import com.example.kitchenmate.datas.CreateFoodRequest
import com.example.kitchenmate.datas.DecreaseFoodRecordByFoodIdRequest
import com.example.kitchenmate.datas.FoodRecordLogItem
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModel
import com.example.kitchenmate.viewModels.CreateFoodActivityViewModelFactory
import com.example.kitchenmate.viewModels.DecreaseFoodRecordActivityViewModel
import com.example.kitchenmate.viewModels.DecreaseFoodRecordActivityViewModelFactory
import com.example.kitchenmate.viewModels.FoodDetailActivityViewModel
import com.example.kitchenmate.viewModels.FoodDetailActivityViewModelFactory
import com.example.kitchenmate.viewModels.FoodRecordActivityViewModel
import com.example.kitchenmate.viewModels.FoodRecordActivityViewModelFactory


class DecreaseFoodRecordActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDecreaseFoodRecordBinding
    private lateinit var mViewModel: DecreaseFoodRecordActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decrease_food_record)

        mBinding = ActivityDecreaseFoodRecordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val foodName = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")
        val amountUnit = intent.getStringExtra("amountUnit")
        val id = intent.getStringExtra("id")
        val imageUrl = intent.getStringExtra("imageUrl")
        val foodRecordId = intent.getStringExtra("foodRecordId")
        val remainingAmount = intent.getStringExtra("remainingAmount").toString()
        val expiryDate = intent.getStringExtra("expiryDate")
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val applyButton = findViewById<Button>(R.id.btnApply)
        val etConsumeAmount = findViewById<EditText>(R.id.etConsumeAmount)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvAmount = findViewById<TextView>(R.id.tvAmount)
        val tvExpiryDate = findViewById<TextView>(R.id.tvExpiryDate)
        tvExpiryDate.text = expiryDate
        tvAmount.text = remainingAmount
        tvName.text = foodName
        mViewModel = ViewModelProvider(this, DecreaseFoodRecordActivityViewModelFactory(FoodRepository(APIService.getService(), application), application))[DecreaseFoodRecordActivityViewModel::class.java]


        applyButton.setOnClickListener{
            val decreaseAmount : Int
            try {
                decreaseAmount = etConsumeAmount.text.toString().toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "You must enter an integer!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (remainingAmount.toString().toInt()-decreaseAmount < 0) {
                Toast.makeText(this, "You don't have enough amount!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (foodRecordId != null) {
                mViewModel.decreaseFoodRecord((DecreaseFoodRecordByFoodIdRequest(amount = decreaseAmount)),foodRecordId)
            }
            val intent = Intent(this, FoodRecordActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", (amount.toString().toInt()-decreaseAmount).toString())
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            intent.putExtra("imageUrl", imageUrl)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, FoodRecordActivity::class.java)
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



