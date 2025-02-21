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

object BookMarkData {

    fun saveBookMarkNews(article: Article) {
        FirebaseDatabase.getInstance().getReference("BookMarkNews")
            .child("SaveBookMark/${article.publishedAt}").setValue(article)
            .addOnSuccessListener {

            }
    }

    fun removeBookMarkNews(article: Article){
        FirebaseDatabase.getInstance().getReference("BookMarkNews")
            .child("SaveBookMark/${article.publishedAt}")
            .removeValue()
            .addOnSuccessListener {
                // Action on successful deletion
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("FirebaseError", "Failed to remove bookmark: ${exception.message}")
            }
    }

    fun fetchBookMarkNews(): Flow<List<Article?>> = callbackFlow {
        val db = FirebaseDatabase.getInstance().getReference("BookMarkNews").child("SaveBookMark")
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val articles = snapshot.children.mapNotNull { it.getValue(Article::class.java) }

                val article = ArrayList<Article?>()
                Log.e("prodData", "onDataChange: ${snapshot.value}")
                for (articles in snapshot.children) {
                    val news = articles.getValue(Article::class.java)
                    article.add(news!!)
                }
                trySend(articles).isSuccess

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