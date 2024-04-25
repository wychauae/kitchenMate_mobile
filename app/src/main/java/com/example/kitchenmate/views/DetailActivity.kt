package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val foodName = intent.getStringExtra("foodName")
        val foodPhoto = intent.getStringExtra("photo")
        val ingredient = intent.getStringExtra("ingredient")
        val step = intent.getStringExtra("step")

        val ingredient_button = findViewById<Button>(R.id.btnIngredient)
        val stpes_button = findViewById<Button>(R.id.btnStep)
        val description_text: TextView = findViewById<TextView>(R.id.txvDescription)

        val back_button = findViewById<ImageButton>(R.id.backButton)
        val food_name: TextView = findViewById<TextView>(R.id.barFoodName)
        val bookMark_Button = findViewById<ImageButton>(R.id.bookMarkButton)

        val food_image = findViewById<ImageView>(R.id.ivFoodImage)

        food_name.setText(foodName)
        description_text.setText(ingredient)
        if (foodPhoto != null) {
            food_image.setImageResource(foodPhoto.toInt())
        }


        ingredient_button.setOnClickListener {
            description_text.setText(ingredient)
        }
        stpes_button.setOnClickListener {
            Log.d("hello", "sjsjsjs")
            description_text.setText(step)
        }
        bookMark_Button.setOnClickListener {
            description_text.setText("Bookmark button is clicked")
        }
        back_button.setOnClickListener {
            val it = Intent(this, RecipeRecyclerViewActivity::class.java)
            startActivity(it)
        }

    }

}



