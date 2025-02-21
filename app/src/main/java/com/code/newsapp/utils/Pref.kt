package com.code.newsapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.code.newsapp.presentation.news.model.other.BookMark
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Pref {

    private lateinit var sharedPreferences: SharedPreferences

    fun setInit(activity: Activity){
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE) ?: return
    }

    fun setString(key: String, data: String) {
        with(sharedPreferences.edit()) {
            putString(key, data)
            apply()
        }
    }

    fun getString(key: String, data: String): String {
        return sharedPreferences.getString(key, data).toString()
    }

    fun bookMarkArrayList(list: List<BookMark>, key: String) {
        val editor : Editor = Pref.sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }


    fun getBookMarkArrayList(key: String): List<BookMark> {
        val gson = Gson()
        val json: String = Pref.sharedPreferences.getString(key,"").toString()
        val type: Type = object : TypeToken<List<BookMark>>() {}.type
        return gson.fromJson(json, type)
    }
}

