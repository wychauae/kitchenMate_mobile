package com.example.kitchenmate.datas

import java.util.Date

data class InsertFoodRecordRequest(
    val foodId: String,
    val amount: Int,
    val expiredDate: Date
)