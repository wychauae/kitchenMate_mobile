//package com.example.kitchenmate.views
//
//import com.example.kitchenmate.R
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class RecipeRecyclerViewActivity : AppCompatActivity() {
//
//    private lateinit var recycler_view_Recipe: RecyclerView
//    private lateinit var recipeAdapter: RecipeAdapter
//    private lateinit var recipeList: ArrayList<itemRecipe>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.recycler_recipe)
//
//        val ingredientList = listOf<String>("Ingredient 1", "Ingredient 2","Ingredient 3","Ingredient 4","Ingredient 5","Ingredient 6","Ingredient 7","Ingredient 8","Ingredient 9","Ingredient 10")
//        val step = listOf<String>("step 1", "step 2","step 3","step 4","step 5","step 6","step 7","step 8","step 9","step 10")
//        recycler_view_Recipe = findViewById(R.id.rvRecipe)
//        recipeList = ArrayList()
//
//        val layoutManager = LinearLayoutManager(this)
//        recycler_view_Recipe.layoutManager = layoutManager
//
//        recipeAdapter = RecipeAdapter(recipeList)
//        recycler_view_Recipe.adapter = recipeAdapter
//
//        for (i in 1..10){
//            val imageName = "food$i"
//            val imageId = resources.getIdentifier(imageName, "drawable", packageName)
//            recipeList.add(itemRecipe(imageName, imageId, ingredientList[i-1], step[i-1]))
//        }
//        recipeAdapter.notifyDataSetChanged()
//    }
//
//}
//
//
//
