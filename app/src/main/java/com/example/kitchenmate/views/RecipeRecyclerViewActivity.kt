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
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.datas.GetAllRecipeRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.DetailActivityViewModel
import com.example.kitchenmate.viewModels.DetailActivityViewModelFactory

class RecipeRecyclerViewActivity : AppCompatActivity() {

    private lateinit var recycler_view_Recipe: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: ArrayList<itemRecipe>
    private lateinit var mViewModel: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_recipe)

//        mViewModel =  ViewModelProvider(this, DetailActivityViewModelFactory(AuthRepository(
//            APIService.getService()), application))[DetailActivityViewModel::class.java]
//        mViewModel.requestAllRecipe(GetAllRecipeRequest(all = "dummy data"))

        // can receive id, name, steps, imageURL
        //call API
//        "recipeList": [
//        {
//            "_id": "663b2e6706aeec08f15f36e6",
//            "name": "Egg fried rice",
//            "steps": [
//            "Cook the rice ",
//            "Heat 2 tbsp of the oil"
//            ],
//            "imageUrl": "uploads\\undefined\\undefined\\2024-05-08-154855-Egg fried rice.jpg"
//        }
//        ]

        val ingredientList: List<itemIngredient> = listOf(itemIngredient("ingredient 1", "amount 1", "unit","enough"),itemIngredient("ingredient 2", "amount 2", "unit","enough"),itemIngredient("ingredient 3", "amount 3", "unit","enough"),
            itemIngredient("ingredient 4", "amount 4", "unit","not enough"),itemIngredient("ingredient 5", "amount 5", "unit","enough"),itemIngredient("ingredient 6", "amount 6", "unit","enough"),
            itemIngredient("ingredient 7", "amount 7", "unit","not enough"),itemIngredient("ingredient 8", "amount 8", "unit","enough"),itemIngredient("ingredient 9", "amount 9", "unit","enough"),itemIngredient("ingredient 10", "amount 10", "unit","enough"))
        val step: List<String> = listOf("1. Firstly lets try whether this line will be too long to put or not la","step 2","step 3","step 4","step 5","step 6","step 7","step 8","step 9","step 10")

        recycler_view_Recipe = findViewById(R.id.rvRecipe)
        recipeList = ArrayList()

        val layoutManager = LinearLayoutManager(this)
        recycler_view_Recipe.layoutManager = layoutManager

        recipeAdapter = RecipeAdapter(recipeList)
        recycler_view_Recipe.adapter = recipeAdapter

        for (i in 1..10){
            val recipeName = "food$i"
            val image = resources.getIdentifier(recipeName, "drawable", packageName)
            recipeList.add(itemRecipe(recipeName, image, "id" ,ingredientList, step))
        }
        recipeAdapter.notifyDataSetChanged()

        // create button to create a new recipe
        val creteButton = findViewById<ImageButton>(R.id.creteButton)
        creteButton.setOnClickListener {
            val it = Intent(this, CreateRecipeActivity::class.java)
            startActivity(it)
        }
    }

}



