package com.example.testapp.utils

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

fun addItemToDb(
    db: FirebaseFirestore,
    context: Context,
    item: HashMap<String, Any>,
    userId: String
) {
    (item["id"] as? String)?.let {
        db.collection(userId).document(it).set(item).addOnSuccessListener {
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
}

fun updateItemOfDb(
    db: FirebaseFirestore,
    context: Context,
    item: HashMap<String, Any>,
    userId: String
) {
    Log.d(TAG, "Db item ::: $item");

    (item["id"] as? String)?.let {
        db.collection(userId).document(it).update(item)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Updated Item successfully.",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}

fun getItemsFromDb(
    db: FirebaseFirestore,
    userId: String,
    onItemsFetched: (items: List<Map<String, Any>>) -> Unit
) {
    val listOfItems = mutableListOf<Map<String, Any>>()

    db.collection(userId)
        .get()
        .addOnSuccessListener { result ->
            Log.d(TAG, "Document info::: $result")

            for (document in result) {
                listOfItems.add(document.data)
            }

            onItemsFetched(listOfItems)
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun getSingleItemFromDb(
    db: FirebaseFirestore,
    userId: String,
    docId: String,
    onItemFetched: (item: Map<String, Any>) -> Unit
) {
    db.collection(userId).document(docId)
        .get()
        .addOnSuccessListener { result ->
            if (result.data != null) {
                onItemFetched(result.data!!)
            } else {
                throw NoSuchElementException("Document exists but data is null for docId: $docId")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun deleteItemFromDb(
    db: FirebaseFirestore, userId: String,
    docId: String,
    onItemDeleted: (msg: String) -> Unit
) {
    db.collection(userId).document(docId).delete()
        .addOnSuccessListener {
            onItemDeleted("Deleted item successfully.");
        }
        .addOnFailureListener { exception ->
            val msg = "Error deleting document."
            Log.w(TAG, msg, exception)
            onItemDeleted(msg)
        }
}