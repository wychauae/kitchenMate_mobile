package com.example.kitchenmate.views

import android.graphics.Color
import android.util.Log
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
        Log.d("enough is", ingredientList[position].enough);
        if(ingredientList[position].enough == "enough"){
            Log.d("enough is", "inside enough");
//            holder.ingredient_name.setTextColor(Color.parseColor("#36e622"))
            holder.recycler_LinearLayout.setBackgroundColor(Color.parseColor("#36e622"));
        }
        else if(ingredientList[position].enough == "not enough"){
            Log.d("enough is", "inside  not enough");
//            holder.ingredient_name.setTextColor(Color.parseColor("#bd3737"))
            holder.recycler_LinearLayout.setBackgroundColor(Color.parseColor("#ff6e6e"));
        }
        else if(ingredientList[position].enough == "not available"){
            Log.d("enough is", "inside  not available");
//            holder.ingredient_name.setTextColor(Color.parseColor("#787878"))
            holder.recycler_LinearLayout.setBackgroundColor(Color.parseColor("#b5b5b5"));
        }
        holder.ingredient_amount.text = ingredientList[position].amount + ingredientList[position].amountUnit
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }
}
