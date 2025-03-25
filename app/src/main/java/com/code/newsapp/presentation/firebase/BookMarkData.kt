package com.code.newsapp.presentation.firebase

import android.util.Log
import com.code.newsapp.presentation.news.model.response.Article
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

object BookMarkData {

    fun saveBookMarkNews(article: Article, uniqueID: String) {

        FirebaseDatabase.getInstance().getReference("BookMarkNews")
            .child("DeviceID/${UUID.randomUUID()}")
            .child("TimeStamp/${System.currentTimeMillis()}").setValue(article)
            .addOnSuccessListener {

            }
    }

    fun removeBookMarkNews(orCreateUniqueID: String) {
        FirebaseDatabase.getInstance().getReference("BookMarkNews").
        child("DeviceID/${UUID.randomUUID()}")
            .child("TimeStamp/${System.currentTimeMillis()}")
            .removeValue()
            .addOnSuccessListener {
                Log.d("Firebase", "Data removed successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to remove data ${exception.message}")
            }

    }

    fun fetchBookMarkNews(): Flow<List<Article?>> = callbackFlow {
        val db = FirebaseDatabase.getInstance().getReference("BookMarkNews").child("SaveBookMark")
            .child("TimeStamp")

        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val articles = snapshot.children.mapNotNull { it.getValue(Article::class.java) }

                val article = ArrayList<Article?>()
                Log.e("prodData", "onDataChange: ${snapshot.value}")
                for (articles in snapshot.children) {
                    val news = articles.getValue(Article::class.java)
                    article.add(news!!)
                }
                trySend(article).isSuccess

            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        db.addValueEventListener(eventListener)

        awaitClose {
            db.removeEventListener(eventListener)
        }
    }


}