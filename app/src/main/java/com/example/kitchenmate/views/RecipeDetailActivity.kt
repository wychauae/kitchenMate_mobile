package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.databinding.ActivityDetailBinding
import com.example.kitchenmate.databinding.ActivityLoginBinding
import com.example.kitchenmate.datas.RecipeDetailItem
import com.example.kitchenmate.datas.RecipeIngredient
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModel
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModelFactory

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var recycler_view_Recipe_Ingredient: RecyclerView
    private lateinit var ingredientAdapter: RecipeIngredientAdapter
    private lateinit var ingredient_List: ArrayList<itemIngredient>
    private lateinit var step_List: ArrayList<itemIngredient>
    private lateinit var stepAdapter: RecipeIngredientAdapter
    private var isBookmarked: Boolean = false
    private lateinit var mBinding: ActivityDetailBinding
    private lateinit var mViewModel: RecipeDetailActivityViewModel

    private lateinit var recipeID:String
//    private lateinit var foodName:String
//    private lateinit var food_Photo:String
//    private lateinit var ingredient_list :ArrayList<RecipeIngredient>
//    private lateinit var step_list :ArrayList<String>
//    private lateinit var is_Bookmarked: Boolean



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        val ingredient_button = findViewById<Button>(R.id.btnIngredient)
//        val steps_button = findViewById<Button>(R.id.btnStep)
//        val description_text: TextView = findViewById<TextView>(R.id.txvDescription)
//        val amount_text: TextView = findViewById<TextView>(R.id.txvIngredient)
//
//        val back_button = findViewById<ImageButton>(R.id.backButton)
//        val bookMark_Button = findViewById<ImageButton>(R.id.bookMarkButton)
//        val touch_Button = findViewById<ImageButton>(R.id.touchButton)
//
//        val food_image = findViewById<ImageView>(R.id.ivFoodImage)

        mBinding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
        mViewModel = ViewModelProvider(this, RecipeDetailActivityViewModelFactory(RecipeRepository(APIService.getService(), application), application))[RecipeDetailActivityViewModel::class.java]
        recipeID = intent.getStringExtra("recipeID")!!
        getRecipeDetail()

        //        this part is for recycler view


        val dummy_ingredientList: List<List<String>> = listOf(listOf("ingredient 1", "amount 1", "unit","enough"),listOf("ingredient 2", "amount 2", "unit","enough"),listOf("ingredient 3", "amount 3", "unit","enough"),
            listOf("ingredient 4", "amount 4", "unit","not enough"),listOf("ingredient 5", "amount 5", "unit","enough"),listOf("ingredient 6", "amount 6", "unit","enough"),
            listOf("ingredient 7", "amount 7", "unit","not enough"),listOf("ingredient 8", "amount 8", "unit","enough"),listOf("ingredient 9", "amount 9", "unit","enough"),listOf("ingredient 10", "amount 10", "unit","enough"))
        val dummy_stepList: List<String> = listOf("1. Firstly lets try whether this line will be too long to put or not la","step 2","step 3","step 4","step 5","step 6","step 7","step 8","step 9","step 10")
        recycler_view_Recipe_Ingredient = findViewById(R.id.rvRecipeIngredient)
        ingredient_List = ArrayList()
        step_List = ArrayList()

        val layoutManager = LinearLayoutManager(this)
        recycler_view_Recipe_Ingredient.layoutManager = layoutManager

        ingredientAdapter = RecipeIngredientAdapter(ingredient_List)
        recycler_view_Recipe_Ingredient.adapter = ingredientAdapter

        stepAdapter = RecipeIngredientAdapter(step_List)
        recycler_view_Recipe_Ingredient.adapter = ingredientAdapter

        for (i in 1..10){
            ingredient_List.add(itemIngredient(ingredientName=dummy_ingredientList[i-1][0], amount=dummy_ingredientList[i-1][1], amountUnit=dummy_ingredientList[i-1][2], enough=""))
            step_List.add(itemIngredient(ingredientName=dummy_stepList[i-1],amount="", amountUnit="", enough=""))
        }
        ingredientAdapter.notifyDataSetChanged()

        // end of recycler view part

//        food_name.setText(foodName)
//        description_text.setText("ingredient")
//        if (foodPhoto != null) {
//            food_image.setImageResource(foodPhoto.toInt())
//        }


//        ingredient_button.setOnClickListener {
//            description_text.setText("ingredient")
//            amount_text.setText("amount")
//
//            recycler_view_Recipe_Ingredient.adapter = ingredientAdapter
//            ingredientAdapter.notifyDataSetChanged()
//        }
//        steps_button.setOnClickListener {
//            description_text.setText("step")
//            amount_text.setText("")
//
//            recycler_view_Recipe_Ingredient.adapter = stepAdapter
//            stepAdapter.notifyDataSetChanged()
//        }
//        bookMark_Button.setOnClickListener {
//            if(isBookmarked == false){
//                isBookmarked = true
//                bookMark_Button.setImageResource(R.drawable.baseline_bookmark_click)
//                Toast.makeText(this, "add the bookmark", Toast.LENGTH_SHORT).show()
//            }else{
//                isBookmarked = false
//                bookMark_Button.setImageResource(R.drawable.baseline_bookmark);
//                Toast.makeText(this, "remove the bookmark", android.widget.Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        back_button.setOnClickListener {
//            val it = Intent(this, HomeActivity::class.java)
//            startActivity(it)
//        }
//        touch_Button.setOnClickListener {
//            // call API
//            val dummy_ingredientList_after_touch: List<List<String>> = listOf(listOf("ingredient 1", "amount 1", "unit","enough"),listOf("ingredient 2", "amount 2", "unit","enough"),listOf("ingredient 3", "amount 3", "unit","enough"),
//                listOf("ingredient 4", "amount 4", "unit","not enough"),listOf("ingredient 5", "amount 5", "unit","enough"),listOf("ingredient 6", "amount 6", "unit","enough"),
//                listOf("ingredient 7", "amount 7", "unit","not enough"),listOf("ingredient 8", "amount 8", "unit","enough"),listOf("ingredient 9", "amount 9", "unit","enough"),listOf("ingredient 10", "amount 10", "unit","enough"))
//            //clear the original array first
//            ingredient_List.clear()
//            for (i in 1..10){
//                ingredient_List.add(itemIngredient(ingredientName=dummy_ingredientList_after_touch[i-1][0], amount=dummy_ingredientList_after_touch[i-1][1], amountUnit=dummy_ingredientList_after_touch[i-1][2], enough=dummy_ingredientList_after_touch[i-1][3]))
//            }
//            ingredientAdapter.notifyDataSetChanged()
//            Toast.makeText(this, "I want to make this", Toast.LENGTH_SHORT).show()
//        }
        setUpObservers()


    }


    private fun setUpObservers(){
        Log.d("detail recipeID",
            "here"
        )
        mViewModel.getIsLoading().observe(this){
//            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsSuccess().observe(this){
//            var recipeDetailItem = mViewModel.getRecipeDetailItem().value!!
            Log.d("getIsSuccess",
                mViewModel.getRecipeDetailItem().toString()
            )
            mViewModel.getRecipeDetailItem().observe(this){
                val food_name: TextView = findViewById<TextView>(R.id.barFoodName)
                val ingredient_button = findViewById<Button>(R.id.btnIngredient)
                val steps_button = findViewById<Button>(R.id.btnStep)
                val description_text: TextView = findViewById<TextView>(R.id.txvDescription)
                val amount_text: TextView = findViewById<TextView>(R.id.txvIngredient)

                val back_button = findViewById<ImageButton>(R.id.backButton)
                val bookMark_Button = findViewById<ImageButton>(R.id.bookMarkButton)
                val touch_Button = findViewById<ImageButton>(R.id.touchButton)

                val food_image = findViewById<ImageView>(R.id.ivFoodImage)
                food_name.text = it.name
            }
//            if(it){
//                mViewModel.getRecipeDetail().value?.name?.let { it1 ->
//                    Log.d("detail recipeID",
//                        mViewModel.getRecipeDetail().value?.name!!
//                    )
//                }
//                mBinding.barFoodName.text = mViewModel.getRecipeDetail().value?.name
//            } else {
//                mViewModel.getRecipeDetail().value?.name?.let { it1 ->
//                    Log.d("detail recipeID",
//                        mViewModel.getRecipeDetail().value?.name!!
//                    )
//                }
//                mBinding.barFoodName.text = mViewModel.getRecipeDetail().value?.name
//            }
        }

    }

    private fun getRecipeDetail() {
        Log.d("detail name",AuthToken.getInstance(application.baseContext).username.toString())
//        Log.d("detail recipeID",recipeID)
        mViewModel.getRecipeDetail(AuthToken.getInstance(application.baseContext).username!!,recipeID!!)
//        mViewModel.getRecipeDetail()

//        foodName = mViewModel.getRecipeDetail().value?.name!!
//        food_Photo = mViewModel.getRecipeDetail().value?.imageUrl!!
//        ingredient_list = mViewModel.getRecipeDetail().value?.ingredients!!
//        step_list = mViewModel.getRecipeDetail().value?.steps!!
//        is_Bookmarked = mViewModel.getRecipeDetail().value?.isBookmarked

//        Log.d("detail photo",food_Photo.toString())
//        Log.d("detail ingredient_list",ingredient_list.toString())
//        Log.d("detail step_list",step_list.toString())
//        Log.d("detail is_Bookmarked",is_Bookmarked.toString())
    }


}



