package com.example.kitchenmate.utils

import android.content.Context
import android.content.SharedPreferences

class AuthToken private constructor(context: Context){
    companion object {
        private const val TOKEN = "TOKEN"
        private const val TOKEN_VALUE = "TOKEN_VALUE"
        private const val USERNAME = "USERNAME"

        private var instance: AuthToken? = null

        fun getInstance(context: Context): AuthToken = instance ?: synchronized(this){
            // invoke the private constructor to create a new instance to AuthToken
            AuthToken(context).apply {
                instance = this
            }
        }
    }

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)

    var token: String? = null
        set(value) = sharedPreference.edit().putString(TOKEN_VALUE, value).apply().also { field = value }
        get() = field ?: sharedPreference.getString(TOKEN_VALUE, null)

    var username: String? = null
        set(value) = sharedPreference.edit().putString(USERNAME, value).apply().also { field = value }
        get() = field ?: sharedPreference.getString(USERNAME, null)
}