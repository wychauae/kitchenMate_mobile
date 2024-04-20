package com.example.kitchenmate.views

import android.content.Context
import android.view.View;
import android.content.Intent
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val receipeList: ArrayList<itemRecipe>): RecyclerView.Adapter<RecipeViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recipe_item, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.receipe_image.setImageResource(receipeList[position].foodPhoto)
        holder.receipe_name.text = receipeList[position].foodName

        holder.itemView.setOnClickListener (){
//            holder.receipe_name.text = receipeList[position].foodPhoto.toString() //has response
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("foodName", receipeList[position].foodName)
            intent.putExtra("photo", receipeList[position].foodPhoto.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return receipeList.size
    }
}
