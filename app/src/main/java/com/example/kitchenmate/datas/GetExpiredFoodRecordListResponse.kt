package com.example.kitchenmate.datas

data class ExpiredFoodRecordItem(
    val _id: String,
    val name: String,
    val amount: String,
    val amountUnit: String,
    val expiredDate: String
)

data class GetExpiredFoodRecordListResponse(val status: Number, val expiredFoodList: List<ExpiredFoodRecordItem>, val error: String)
