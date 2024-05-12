package com.example.kitchenmate.datas
import java.util.Date

data class FoodRecordItem(
    val _id: String,
    val name: String,
    val amount: String,
    val foodId: String,
    val expiredDate: Date
)
data class GetFoodRecordByFoodIdResponse(
    val status: Number,
    val foodRecordList: List<FoodRecordItem>,
    val error: String
)

