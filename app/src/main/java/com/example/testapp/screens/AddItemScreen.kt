package com.example.testapp.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.testapp.components.CustomTextField
import com.example.testapp.ui.theme.BorderDarkColor
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.LightBgColor
import com.example.testapp.ui.theme.LightGrayColor
import com.example.testapp.ui.theme.TextColor3
import com.example.testapp.utils.dashedBorder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.UUID

@Preview(showBackground = true, widthDp = 370, heightDp = 700)
@Composable
fun AddItemScreen(
    onBackBtnClick: () -> Unit = {},
    onAddItemClick: (item: HashMap<String, Any>) -> Unit = {}
) {
    val context = LocalContext.current;

    val storage = FirebaseStorage.getInstance();
    val storageRef = storage.reference;

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                imageUri = uri
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    )

    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("0") }
    var itemDescription by remember { mutableStateOf("") }
    var itemCategory by remember { mutableStateOf("") }

    var supporterName by remember { mutableStateOf("") }
    var supporterContact by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(vertical = 14.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            /*
                Topbar =============================
                (Back button and title)
            */
            Box() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onBackBtnClick,
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, BorderPrimaryColor),
                        modifier = Modifier.width(40.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = LightBgColor
                        )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.size(23.dp)
                        )
                    }
                }

                Text(
                    "Add new item",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            /*
            Form elements ==========================
            ========================================
         */
            Surface(
                onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                color = LightGrayColor, shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .dashedBorder(BorderDarkColor, RoundedCornerShape(9.dp), 1.dp)
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(38.dp),
                            tint = TextColor3
                        )
                        Spacer(modifier = Modifier.size(9.dp))
                        Text(
                            text = "Upload item picture",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextColor3
                        )
                    }
                }
            }

            /*
            Item name, price, category fields ==================
            ====================================================
            */
            CustomTextField(
                value = itemName,
                onChange = { it -> itemName = it },
                label = "Item Name",
                placeholder = "Baby car toy",
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = itemPrice.toString(),
                onChange = { it -> itemPrice = it },
                label = "Price",
                leadingIcon = {
                    Icon(
                        Icons.Default.AttachMoney, contentDescription = null,
                        tint = TextColor3, modifier = Modifier.size(22.dp)
                    )
                },
            )

            CustomTextField(
                value = itemCategory,
                onChange = { it -> itemCategory = it },
                label = "Category",
            )

            CustomTextField(
                value = itemDescription,
                onChange = { it -> itemDescription = it },
                label = "Description",
                modifier = Modifier.height(110.dp)
            )
        }

        // Full width line
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp)
        ) {
            // Fetching width and height for
            // setting start x and end y
            val canvasWidth = size.width
            val canvasHeight = size.height

            // drawing a line between start(x,y) and end(x,y)
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                color = BorderPrimaryColor,
                strokeWidth = 3F
            )
        }


        /*
            Supporter form elements ==========================
            Name, contact ====================================
         */

        Column(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(text = "Supporter", style = MaterialTheme.typography.bodyLarge)

            CustomTextField(
                value = supporterName, onChange = { it -> supporterName = it },
                label = "Full Name",
                placeholder = "John Doe",
                modifier = Modifier.fillMaxWidth(),
                required = false
            )

            CustomTextField(
                value = supporterContact, onChange = { it -> supporterContact = it },
                label = "Contact",
                placeholder = "982302312",
                modifier = Modifier.fillMaxWidth(),
                required = false,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { ->
                    val itemDetails = hashMapOf(
                        "name" to itemName,
                        "price" to itemPrice,
                        "category" to itemCategory,
                        "description" to itemDescription,
                        "picture" to imageUri,
                        "supporter" to mapOf(
                            "name" to supporterName,
                            "contact" to supporterContact
                        )
                    )

                    handleAddItem(
                        onAddItemClick,
                        context,
                        itemDetails,
                        storageRef
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(
                    "Add Item",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                    color = Color.White
                )
            }
        }


    }
}

fun handleAddItem(
    onAddItemClick: (item: HashMap<String, Any>) -> Unit,
    context: Context,
    item: HashMap<String, Any?>,
    storageRef: StorageReference
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
                    Log.d(TAG, "Download image url ::: $url")

                    item["picture"] = url.toString();
                    @Suppress("UNCHECKED_CAST")
                    onAddItemClick(item as HashMap<String, Any>)
                }
        }.addOnFailureListener{
            Toast.makeText(
                context,
                "Failed to upload image.",
                Toast.LENGTH_LONG
            ).show()
        }

    } else {
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
