package com.example.deeperhw.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(private val context: Context) {

    private var prefs: SharedPreferences

    private val TOKEN_NAME = "token_key"

    init {
        prefs = context.getSharedPreferences(TOKEN_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String = prefs.getString(TOKEN_NAME, null).orEmpty()

    fun setToken(tokenValue: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(TOKEN_NAME, tokenValue)
            commit()
        }
    }
}