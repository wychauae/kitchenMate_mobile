package com.example.kitchenmate.datas
import java.util.Date

data class FoodRecordLogItem(
    val _id: String,
    val amount: String,
    val editor: String,
    val foodId: String,
    val date: Date
)
data class GetFoodRecordLogByFoodIdResponse(
    val status: Number,
    val foodRecordLogList: List<FoodRecordLogItem>,
    val error: String
)

