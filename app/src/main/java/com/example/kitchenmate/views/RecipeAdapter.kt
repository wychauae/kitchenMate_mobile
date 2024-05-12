package com.example.kitchenmate.views

import android.content.Intent
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitchenmate.datas.RecipeItem
import com.example.kitchenmate.utils.APIService

class RecipeAdapter(fragment: String ,dataList: List<RecipeItem>): RecyclerView.Adapter<RecipeViewHolder>()  {

    private var dataList: List<RecipeItem>
    private var fragment: String

    fun setSearchList(dataSearchList: List<RecipeItem>) {
        dataList = dataSearchList
        notifyDataSetChanged()
    }

    init {
        this.dataList = dataList
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val imageUrl = APIService.getBaseUrl() + dataList[position].imageUrl.replace("\\", "/")
        Glide.with(holder.itemView).load(imageUrl).into(holder.recipeImage)
        holder.recipeName.text = dataList[position].name

        if(fragment == "Manage"){
            holder.editButton.visibility =  android.view.View.VISIBLE
        }

        holder.editButton.setOnClickListener {
            //enter recipe details page here
            val intent = Intent(holder.itemView.context, EditRecipeActivity::class.java)
            intent.putExtra("recipeID", dataList[position]._id)
            holder.itemView.context.startActivity(intent)
        }
        holder.recipeCard.setOnClickListener {
            //enter recipe details page here
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("recipeID", dataList[position]._id)
            if(fragment == "Recipe"){
                intent.putExtra("fragment message", "Recipe")
            }
            else if(fragment == "Bookmark"){
                intent.putExtra("fragment message", "Bookmark")
            }
            else if(fragment == "Manage"){
                intent.putExtra("fragment message", "Manage")
            }
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
    var editButton: ImageButton

    init {
        recipeImage = itemView.findViewById<ImageView>(R.id.recipeImage)
        recipeName = itemView.findViewById<TextView>(R.id.recipeName)
        recipeCard = itemView.findViewById<CardView>(R.id.recipeCard)
        editButton = itemView.findViewById<ImageButton>(R.id.editButton)
    }
}

