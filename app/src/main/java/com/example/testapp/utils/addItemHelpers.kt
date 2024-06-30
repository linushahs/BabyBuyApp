package com.example.testapp.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.StorageReference
import java.util.UUID


fun handleAddItem(
    onAddItemClick: (item: HashMap<String, Any>) -> Unit,
    context: Context,
    item: HashMap<String, Any?>,
    storageRef: StorageReference,
    onComplete: () -> Unit
) {
    val isValidationError =
        validateItemFields(
            item["name"] as? String ?: "",
            item["category"] as? String ?: "",
            item["description"] as? String ?: "",
            item["price"] as? String ?: "",
            item["picture"] as? Uri
        );

    if (isValidationError == null) {
        val uniqueImageName = UUID.randomUUID();
        val uploadImageTask = storageRef.child("$uniqueImageName").putFile(item["picture"] as Uri);

        uploadImageTask.addOnSuccessListener {
            storageRef.child("$uniqueImageName").downloadUrl.addOnSuccessListener { url ->

                item["picture"] = url.toString();
                item["pictureRef"] = uniqueImageName.toString();
                item["id"] = UUID.randomUUID().toString();

                @Suppress("UNCHECKED_CAST")
                onAddItemClick(item as HashMap<String, Any>)

                onComplete();
            }
        }.addOnFailureListener {
            onComplete();
            Toast.makeText(
                context,
                "Failed to upload image.",
                Toast.LENGTH_LONG
            ).show()
        }

    } else {
        onComplete();
        Toast.makeText(
            context,
            isValidationError,
            Toast.LENGTH_LONG
        ).show()
    }
}

fun validateItemFields(
    itemName: String,
    itemCategory: String,
    itemDescription: String,
    itemPrice: String,
    picture: Uri?
): String? {
    return when {
        picture == null -> {
            "Item picture is required"
        }

        itemName.isBlank() || itemCategory.isBlank() || itemDescription.isBlank() -> {
            "The required fields cannot be empty"
        }

        itemPrice.toFloat() <= 0 -> {
            "Price cannot be $itemPrice"
        }

        else -> null
    }
}