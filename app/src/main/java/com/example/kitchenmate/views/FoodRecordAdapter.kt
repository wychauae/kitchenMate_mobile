package com.example.kitchenmate.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.R
import com.example.kitchenmate.datas.FoodRecordItem
import java.text.SimpleDateFormat

class FoodRecordAdapter(private val recordList: MutableList<FoodRecordItem>,private val onItemClicked: (FoodRecordItem) -> Unit) :
    RecyclerView.Adapter<FoodRecordAdapter.RecordViewHolder>() {

    class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmount: TextView = view.findViewById(R.id.text_view_amount)
        val textViewDate: TextView = view.findViewById(R.id.text_view_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_record_item, parent, false)
        return RecordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentItem = recordList[position]
        holder.textViewAmount.text = currentItem.amount
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = sdf.format(currentItem.expiredDate)
        holder.textViewDate.text = formattedDate

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.bright_vibrant_orange))

        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }
    }

    fun updateData(newRecordList: List<FoodRecordItem>) {
        recordList.clear()
        recordList.addAll(newRecordList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = recordList.size
}