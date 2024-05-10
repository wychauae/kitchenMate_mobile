package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.utils.APIService

class RecipeAdapter(dataList: List<RecipeItem>): RecyclerView.Adapter<RecipeViewHolder>()  {

    private var dataList: List<RecipeItem>

    fun setSearchList(dataSearchList: List<RecipeItem>) {
        dataList = dataSearchList
        notifyDataSetChanged()
    }

    init {
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val imageUrl = APIService.getBaseUrl() + dataList[position].imageUrl.replace("\\", "/")
        Glide.with(holder.itemView).load(imageUrl).into(holder.recipeImage)
        holder.recipeName.text = dataList[position].name
        holder.recipeCard.setOnClickListener {
            //enter recipe details page here
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeID", dataList[position]._id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateRecipeList(newRecipeList: List<RecipeItem>) {
        dataList = newRecipeList
        notifyDataSetChanged()
    }
}


class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recipeImage: ImageView
    var recipeName: TextView
    var recipeCard: CardView

    init {
        recipeImage = itemView.findViewById<ImageView>(R.id.recipeImage)
        recipeName = itemView.findViewById<TextView>(R.id.recipeName)
        recipeCard = itemView.findViewById<CardView>(R.id.recipeCard)
    }
}

