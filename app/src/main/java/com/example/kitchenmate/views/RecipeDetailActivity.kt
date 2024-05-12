package com.example.kitchenmate.views

import android.content.Intent
import android.graphics.BitmapFactory
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
import com.bumptech.glide.Glide
import com.example.kitchenmate.databinding.ActivityDetailBinding
import com.example.kitchenmate.databinding.ActivityLoginBinding
import com.example.kitchenmate.datas.AddBookmarkRequest
import com.example.kitchenmate.datas.RecipeDetailItem
import com.example.kitchenmate.datas.RecipeIngredient
import com.example.kitchenmate.repositories.RecipeRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.utils.AuthToken
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModel
import com.example.kitchenmate.viewModels.RecipeDetailActivityViewModelFactory
import java.net.URL
import kotlin.properties.Delegates

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var recycler_view_Recipe_Ingredient: RecyclerView
    private lateinit var ingredientAdapter: RecipeIngredientAdapter
    private lateinit var adapter_ingredient_List: ArrayList<itemIngredient>
    private lateinit var adapter_step_List: ArrayList<itemIngredient>
    private var adapter_compare_step_List= ArrayList<itemIngredient>()
    private lateinit var stepAdapter: RecipeIngredientAdapter
    private lateinit var mBinding: ActivityDetailBinding
    private lateinit var mViewModel: RecipeDetailActivityViewModel

    private lateinit var recipeID:String
    private lateinit var foodName:String
    private lateinit var food_Photo:String
    private lateinit var bookMark_Button:ImageButton
    private lateinit var ingredient_list :ArrayList<RecipeIngredient>
    private lateinit var step_list :ArrayList<String>
    private lateinit var fragment_message :String
    private var is_Bookmarked by Delegates.notNull<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val ingredient_button = findViewById<Button>(R.id.btnIngredient)
        val steps_button = findViewById<Button>(R.id.btnStep)
        val description_text: TextView = findViewById<TextView>(R.id.txvDescription)
        val amount_text: TextView = findViewById<TextView>(R.id.txvIngredient)

        val back_button = findViewById<ImageButton>(R.id.backButton)
//        val bookMark_Button = findViewById<ImageButton>(R.id.bookMarkButton)
        bookMark_Button = findViewById<ImageButton>(R.id.bookMarkButton)
        val touch_Button = findViewById<ImageButton>(R.id.touchButton)

        recycler_view_Recipe_Ingredient = findViewById(R.id.rvRecipeIngredient)
        adapter_ingredient_List = ArrayList()
        adapter_step_List = ArrayList()

        val layoutManager = LinearLayoutManager(this)
        recycler_view_Recipe_Ingredient.layoutManager = layoutManager

        ingredientAdapter = RecipeIngredientAdapter(adapter_ingredient_List)
        recycler_view_Recipe_Ingredient.adapter = ingredientAdapter

        stepAdapter = RecipeIngredientAdapter(adapter_step_List)
        recycler_view_Recipe_Ingredient.adapter = ingredientAdapter


        mBinding = ActivityDetailBinding.inflate(LayoutInflater.from(this))
        mViewModel = ViewModelProvider(this, RecipeDetailActivityViewModelFactory(RecipeRepository(APIService.getService(), application), application))[RecipeDetailActivityViewModel::class.java]
        recipeID = intent.getStringExtra("recipeID")!!
        fragment_message = intent.getStringExtra("fragment message")!!
        Log.d("fragment message is", fragment_message)
        getRecipeDetail()   // call view model to connect API


        back_button.setOnClickListener {
            if (fragment_message == "Recipe"){
                val it = Intent(this, HomeActivity::class.java)
                it.putExtra("fragment", "Recipe")
                startActivity(it)
            }else if (fragment_message == "Bookmark"){
                val it = Intent(this, HomeActivity::class.java)
                it.putExtra("fragment", "BookMark")
                startActivity(it)
            }else if (fragment_message == "Manage"){
                val it = Intent(this, MyRecipeListActivity::class.java)
                it.putExtra("fragment", "UserProfile")
                startActivity(it)
            }
        }
        ingredient_button.setOnClickListener {
            description_text.setText("ingredient")
            amount_text.setText("amount")

            recycler_view_Recipe_Ingredient.adapter = ingredientAdapter
            ingredientAdapter.notifyDataSetChanged()
        }
        steps_button.setOnClickListener {
            description_text.setText("step")
            amount_text.setText("")

            recycler_view_Recipe_Ingredient.adapter = stepAdapter
            stepAdapter.notifyDataSetChanged()
        }

        bookMark_Button.setOnClickListener {
            if(is_Bookmarked == false){
                is_Bookmarked = true
                //call API to addBookmarkRecipe
                mViewModel.addBookmarkRecipe(AddBookmarkRequest(recipeID))

            }else{
                is_Bookmarked = false
                //call API
                mViewModel.removeBookmarkRecipe(recipeID)
            }
        }


        touch_Button.setOnClickListener {
            // call API
            mViewModel.compare(recipeID)
            ingredientAdapter.notifyDataSetChanged()
        }
        setUpObservers()


    }


    private fun setUpObservers(){
        Log.d("detail recipeID",
            "here"
        )
        mViewModel.getIsLoading().observe(this){
//            mBinding.progressBar.isVisible = it
        }
        mViewModel.getIsAddBookmarkSuccess().observe(this){
            if(it) {
                if (mViewModel.getIsAddBookmarkSuccess().value!!) {
                    bookMark_Button.setImageResource(R.drawable.baseline_bookmark_click)
                    Toast.makeText(this, "add the bookmark", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error during add the bookmark", Toast.LENGTH_LONG).show()
                }
            }
        }
        mViewModel.getIsRemoveBookmarkSuccess().observe(this){
            if(it) {
                if (mViewModel.getIsRemoveBookmarkSuccess().value!!) {
                    bookMark_Button.setImageResource(R.drawable.baseline_bookmark);
                    Toast.makeText(this, "remove the bookmark", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error during add the bookmark", Toast.LENGTH_LONG).show()
                }
            }
        }
        mViewModel.getCompareSuccess().observe(this){
            if(it) {
                if (mViewModel.getCompareSuccess().value!!) {
                    mViewModel.getCompareItemList().observe(this){
                        adapter_compare_step_List.clear()
                        ingredientAdapter = RecipeIngredientAdapter(adapter_compare_step_List)
                        recycler_view_Recipe_Ingredient.adapter = ingredientAdapter
                        for (ingredient in it){
                            adapter_compare_step_List.add(itemIngredient(ingredientName=ingredient.name, amount=ingredient.amount.toString(), amountUnit=ingredient.amountUnit, enough=ingredient.status))
                        }
                        Log.d("adapter_compare_step_List is", adapter_ingredient_List.toString());
                        ingredientAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "Lets make this meal!!", android.widget.Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "Error during compare", Toast.LENGTH_LONG).show()
                }
            }
        }
        mViewModel.getIsSuccess().observe(this){
//            var recipeDetailItem = mViewModel.getRecipeDetailItem().value!!
            Log.d("getIsSuccess",
                mViewModel.getRecipeDetailItem().toString()
            )
            if(it) {
                mViewModel.getRecipeDetailItem().observe(this) {
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
                    ingredient_list = it.ingredients
                    step_list = it.steps
                    is_Bookmarked = it.isBookmarked

                    val imageUrl = APIService.getBaseUrl() + it.imageUrl.replace("\\", "/")
                    Glide.with(findViewById<Button>(R.id.ivFoodImage)).load(imageUrl)
                        .into(food_image)

                    //                Log.d("detail foodName",it.name.toString())
                    Log.d("detail photo", it.imageUrl.toString())
                    Log.d("detail ingredient_list", it.ingredients.toString())
                    Log.d("detail step_list", it.steps.toString())
                    Log.d("detail is_Bookmarked", it.isBookmarked.toString())

                    if (this::ingredient_list.isInitialized && this::step_list.isInitialized) {
                        for (ingredient in ingredient_list) {
                            adapter_ingredient_List.add(
                                itemIngredient(
                                    ingredientName = ingredient.name,
                                    amount = ingredient.amount.toString(),
                                    amountUnit = ingredient.amountUnit,
                                    enough = ""
                                )
                            )
                        }
                        for (step in step_list) {
                            adapter_step_List.add(
                                itemIngredient(
                                    ingredientName = step,
                                    amount = "",
                                    amountUnit = "",
                                    enough = ""
                                )
                            )
                        }
                    }
                    ingredientAdapter.notifyDataSetChanged()

                    if (is_Bookmarked == false) {
                        bookMark_Button.setImageResource(R.drawable.baseline_bookmark)
                    } else {
                        bookMark_Button.setImageResource(R.drawable.baseline_bookmark_click);
                    }
                }
            }
        }

    }

    private fun getRecipeDetail() {
        Log.d("detail name",AuthToken.getInstance(application.baseContext).username.toString())
        mViewModel.getRecipeDetail(AuthToken.getInstance(application.baseContext).username!!,recipeID!!)

    }


}



