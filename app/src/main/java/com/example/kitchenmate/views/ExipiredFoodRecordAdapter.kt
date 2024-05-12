package com.example.kitchenmate.views

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.R
import com.example.kitchenmate.datas.ExpiredFoodRecordItem
import com.example.kitchenmate.repositories.FoodRecordRepository
import com.example.kitchenmate.utils.APIService
import com.example.kitchenmate.viewModels.NotificationActivityViewModel
import com.example.kitchenmate.viewModels.NotificationActivityViewModelFactory

class ExipiredFoodRecordAdapter (dataList: List<ExpiredFoodRecordItem>,  mViewModel: NotificationActivityViewModel) : RecyclerView.Adapter<ExipiredFoodRecordViewHolder>(){
    private var dataList: List<ExpiredFoodRecordItem>
    private var mViewModel: NotificationActivityViewModel
    init {
        this.mViewModel = mViewModel
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExipiredFoodRecordViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_record_item, parent, false)
        return ExipiredFoodRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExipiredFoodRecordViewHolder, position: Int) {
        holder.foodName.text = dataList[position].name
        holder.amount.text = dataList[position].amount
        holder.amountUnit.text = dataList[position].amountUnit
        holder.expiredDate.text = dataList[position].expiredDate
        holder.confirmExpiredBtn.setOnClickListener {
            mViewModel.confirmExpired(dataList[position]._id)
            mViewModel.fetchExpiredFoodRecordList()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateFoodRecordList(newFoodRecordList: List<ExpiredFoodRecordItem>) {
        dataList = newFoodRecordList
        notifyDataSetChanged()
    }
}




class ExipiredFoodRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var foodName: TextView
    var amount: TextView
    var amountUnit: TextView
    var expiredDate: TextView
    var confirmExpiredBtn: ImageButton

    init {
        foodName = itemView.findViewById<TextView>(R.id.foodName)
        amount = itemView.findViewById<TextView>(R.id.foodAmount)
        amountUnit = itemView.findViewById<TextView>(R.id.foodAmountUnit)
        expiredDate = itemView.findViewById<TextView>(R.id.foodExpiredDate)
        confirmExpiredBtn = itemView.findViewById(R.id.confirmExpiredBtn)

    }
}