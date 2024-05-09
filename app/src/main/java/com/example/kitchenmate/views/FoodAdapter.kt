package com.example.kitchenmate.views

import android.util.Log
import com.example.kitchenmate.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitchenmate.datas.FoodItem
import com.example.kitchenmate.utils.APIService


class FoodAdapter(dataList: List<FoodItem>) : RecyclerView.Adapter<FoodViewHolder>() {

    private var dataList: List<FoodItem>
    fun setSearchList(dataSearchList: List<FoodItem>) {
        dataList = dataSearchList
        notifyDataSetChanged()
    }

    init {
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val imageUrl = APIService.getBaseUrl() + dataList[position].imageUrl.replace("\\", "/")
        Glide.with(holder.itemView).load(imageUrl).into(holder.foodImage)
        holder.foodName.text = dataList[position].name
        holder.foodDescription.text = dataList[position].description
        holder.amount.text = dataList[position].amount
        holder.amountUnit.text = dataList[position].amountUnit
        holder.foodCard.setOnClickListener {
            //enter food details page here
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateFoodList(newFoodList: List<FoodItem>) {
        dataList = newFoodList
        notifyDataSetChanged()
    }
}

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var foodImage: ImageView
    var foodName: TextView
    var foodDescription: TextView
    var amount: TextView
    var amountUnit: TextView
    var foodCard: CardView

    init {
        foodImage = itemView.findViewById<ImageView>(R.id.foodImage)
        foodName = itemView.findViewById<TextView>(R.id.foodName)
        foodDescription = itemView.findViewById<TextView>(R.id.foodDesc)
        amount = itemView.findViewById<TextView>(R.id.foodAmount)
        amountUnit = itemView.findViewById<TextView>(R.id.foodAmountUnit)
        foodCard = itemView.findViewById<CardView>(R.id.foodCard)
    }
}