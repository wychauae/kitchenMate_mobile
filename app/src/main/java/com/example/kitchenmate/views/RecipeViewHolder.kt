package com.example.kitchenmate.views

import com.example.kitchenmate.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val receipe_image: ImageView = itemView.findViewById(R.id.item_recipe_image)
    val receipe_name: TextView = itemView.findViewById(R.id.item_recipe_FoodName)
}