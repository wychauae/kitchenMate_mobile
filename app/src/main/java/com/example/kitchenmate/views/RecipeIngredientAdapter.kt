package com.example.kitchenmate.views

import android.graphics.Color
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecipeIngredientAdapter(private val ingredientList: ArrayList<itemIngredient>): RecyclerView.Adapter<RecipeIngredientViewHolder>()  {
    //testing

//    private lateinit var mViewModel: DetailActivityViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recipe_ingredient_item, parent, false)
        return RecipeIngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeIngredientViewHolder, position: Int) {
        holder.ingredient_name.text = ingredientList[position].ingredientName
        if(ingredientList[position].enough == "enough"){
            holder.ingredient_name.setTextColor(Color.parseColor("#0aad3f"))
        }
        else if(ingredientList[position].enough == "not enough"){
            holder.ingredient_name.setTextColor(Color.parseColor("#ffff01"))
        }
        holder.ingredient_amount.text = ingredientList[position].amount + ingredientList[position].amountUnit
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }
}
