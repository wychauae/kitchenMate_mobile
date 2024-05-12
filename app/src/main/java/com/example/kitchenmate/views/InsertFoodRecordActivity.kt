package com.example.kitchenmate.views


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kitchenmate.R
import com.example.kitchenmate.databinding.ActivityInsertFoodRecordBinding
import com.example.kitchenmate.datas.InsertFoodRecordRequest
import com.example.kitchenmate.repositories.FoodRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.InsertFoodRecordActivityViewModel
import com.example.kitchenmate.viewModels.InsertFoodRecordActivityViewModelFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class InsertFoodRecordActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityInsertFoodRecordBinding
    private lateinit var mViewModel: InsertFoodRecordActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_food_record)

        mBinding = ActivityInsertFoodRecordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val foodName = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")
        val amountUnit = intent.getStringExtra("amountUnit")
        val id = intent.getStringExtra("id")
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val applyButton = findViewById<Button>(R.id.btnApply)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val tvName = findViewById<TextView>(R.id.tvName)
        val etDate = findViewById<EditText>(R.id.etDate)

        tvName.text = foodName
        mViewModel = ViewModelProvider(this, InsertFoodRecordActivityViewModelFactory(FoodRepository(APIService.getService(), application), application))[InsertFoodRecordActivityViewModel::class.java]


        applyButton.setOnClickListener{
            val dateString = etDate.text.toString()
            val date = parseDate(dateString)
            if (date == null) {
                Toast.makeText(this, "You must enter a valid expiry date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val insertAmount : Int
            try {
                insertAmount = etAmount.text.toString().toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "You must enter an integer!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (id != null) {
                mViewModel.insertFoodRecord((InsertFoodRecordRequest(amount = insertAmount, expiredDate = date,foodId = id)))
            }
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", (amount.toString().toInt()+insertAmount).toString())
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra("name", foodName)
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("amountUnit", amountUnit)
            intent.putExtra("id", id)
            startActivity(intent)
        }


    }
    private fun parseDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        dateFormat.isLenient = false // Set lenient to false to strictly parse dates

        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }


    fun showDatePickerDialog(v: View?) {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                var month = month
                month = month + 1
                val date = "$dayOfMonth/$month/$year"
                val etDate = findViewById<EditText>(R.id.etDate)
                etDate.setText(date)
            }, year, month, day
        )
        datePickerDialog.show()
    }
}



