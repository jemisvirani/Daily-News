package com.code.newsapp.all.news.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.code.newsapp.all.news.model.response.NewsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Pref {

    private lateinit var sharedPreferences: SharedPreferences

    fun setInit(activity: Activity) {
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

    fun saveBookMarkArrayList(list: List<NewsItem>, key: String) {
        val editor: Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }


    fun getBookMarkArrayList(key: String): List<NewsItem> {
        val gson = Gson()
        val json: String? =
            sharedPreferences.getString(key, null)

        if (json.isNullOrEmpty()) {
            return emptyList()
        }

        return try {
            val type: Type = object : TypeToken<List<NewsItem>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

