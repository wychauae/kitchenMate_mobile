package com.example.kitchenmate.views

import com.example.kitchenmate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeRecyclerViewActivity : AppCompatActivity() {

    private lateinit var recycler_view_Recipe: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: ArrayList<itemRecipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_recipe)

        recycler_view_Recipe = findViewById(R.id.rvRecipe)
        recipeList = ArrayList()

        val layoutManager = LinearLayoutManager(this)
        recycler_view_Recipe.layoutManager = layoutManager

        recipeAdapter = RecipeAdapter(recipeList)
        recycler_view_Recipe.adapter = recipeAdapter

        for (i in 1..10){
            val imageName = "food$i"
            val imageId = resources.getIdentifier(imageName, "drawable", packageName)
            recipeList.add(itemRecipe(imageName, imageId))
        }
        recipeAdapter.notifyDataSetChanged()
    }

}



