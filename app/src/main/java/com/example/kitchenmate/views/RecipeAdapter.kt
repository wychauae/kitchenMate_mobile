package com.example.kitchenmate.views

import android.content.Context
import android.view.View;
import android.content.Intent
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

class RecipeAdapter(private val receipeList: ArrayList<itemRecipe>): RecyclerView.Adapter<RecipeViewHolder>()  {
    //testing

    private lateinit var mViewModel: DetailActivityViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recipe_item, parent, false)
//        mViewModel =  ViewModelProvider(this, DetailActivityViewModelFactory(AuthRepository(APIService.getService()), application))[DetailActivityViewModel::class.java]
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.receipe_image.setImageResource(receipeList[position].foodPhoto)
        holder.receipe_name.text = receipeList[position].foodName

        holder.itemView.setOnClickListener (){
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            //intent.putExtra("id", receipeList[position].recipeID)
            intent.putExtra("foodName", receipeList[position].foodName)
            intent.putExtra("photo", receipeList[position].foodPhoto.toString())
//            intent.putExtra("ingredient", receipeList[position].ingredient)
//            intent.putExtra("step", receipeList[position].step)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return receipeList.size
    }
}
