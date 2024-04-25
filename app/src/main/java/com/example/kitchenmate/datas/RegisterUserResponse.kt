package com.example.kitchenmate.datas

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(@SerializedName("status") val status: Number, @SerializedName("error") val error: String)
