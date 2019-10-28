package com.example.capitalsocial.utilities

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    val PREFS_FILENAME = "capital_social"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var ID: String = "id"
    var TOKEN: String = "token"

    var id: Int
        get() = prefs.getInt(ID, 0)
        set(value) = prefs.edit().putInt(ID, value).apply()

    var token: String
        get() = prefs.getString(TOKEN, "")!!
        set(value) = prefs.edit().putString(TOKEN, value).apply()

}