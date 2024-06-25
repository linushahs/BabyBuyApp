package com.example.testapp.utils

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

fun addItemToDb(db: FirebaseFirestore, context: Context, item: HashMap<String, Any>, userId: String) {
    db.collection(userId)
        .add(item)
        .addOnSuccessListener {
            Toast.makeText(
                context,
                "Added Item successfully.",
                Toast.LENGTH_LONG
            ).show()
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

fun getItemsFromDb(db: FirebaseFirestore, userId: String, onItemsFetched: (items: List<Map<String, Any>>) -> Unit){
    val listOfItems = mutableListOf<Map<String, Any>>()

    db.collection(userId)
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "Document info::: ${document.data}")
                listOfItems.add(document.data)
            }

            onItemsFetched(listOfItems)
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}