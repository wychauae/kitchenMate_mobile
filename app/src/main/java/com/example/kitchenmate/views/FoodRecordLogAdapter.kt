package com.example.kitchenmate.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchenmate.R
import com.example.kitchenmate.datas.FoodRecordLogItem
import java.text.SimpleDateFormat

class FoodRecordLogAdapter(private val logList: MutableList<FoodRecordLogItem>) :
    RecyclerView.Adapter<FoodRecordLogAdapter.LogViewHolder>() {

    class LogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmount: TextView = view.findViewById(R.id.text_view_amount)
        val textViewDate: TextView = view.findViewById(R.id.text_view_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_record_log_item, parent, false)
        return LogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val currentItem = logList[position]
        holder.textViewAmount.text = currentItem.amount
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = sdf.format(currentItem.date)
        holder.textViewDate.text = formattedDate

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))

        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.bright_vibrant_orange))
        }
    }

    fun updateData(newLogList: List<FoodRecordLogItem>) {
        logList.clear()
        logList.addAll(newLogList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = logList.size
}