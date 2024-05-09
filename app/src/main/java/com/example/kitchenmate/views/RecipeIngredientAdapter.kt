package com.example.kitchenmate.views

import android.content.Context
import android.view.View;
import android.content.Intent
import android.graphics.Color
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.datas.LoginUserRequest
import com.example.kitchenmate.repositories.AuthRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.DetailActivityViewModel
import com.example.kitchenmate.viewModels.DetailActivityViewModelFactory
import com.example.kitchenmate.viewModels.LoginActivityViewModel
import com.example.kitchenmate.viewModels.LoginActivityViewModelFactory

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
