package com.example.testapp.screens

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.BabyBuyScreen
import com.example.testapp.LocalGoogleAuthUiClient
import com.example.testapp.LocalNavController
import com.example.testapp.R
import com.example.testapp.auth.FirebaseAuthClient
import com.example.testapp.components.FavButtonVariants
import com.example.testapp.components.FavoriteButton
import com.example.testapp.components.ThreeDotsDropdown
import com.example.testapp.components.ViewRating
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.DisabledPrimaryColor
import com.example.testapp.ui.theme.LightBgColor
import com.example.testapp.ui.theme.LightGrayColor
import com.example.testapp.ui.theme.LightPrimaryColor
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.ui.theme.TextColor1
import com.example.testapp.ui.theme.TextColor2
import com.example.testapp.utils.GoogleMapBox
import com.example.testapp.utils.deleteItemFromDb
import com.example.testapp.utils.getItemsFromDb
import com.example.testapp.utils.getSingleItemFromDb
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

@Preview(showBackground = true, widthDp = 370, heightDp = 700)
@Composable
fun ItemDetailsScreen(
    onBackBtnClick: () -> Unit = {},
    itemId: String? = null
) {
    var itemDetails by remember { mutableStateOf<Map<String, Any>?>(null) }
    val db = FirebaseFirestore.getInstance();
    val googleAuthUiClient = LocalGoogleAuthUiClient.current;
    val navController = LocalNavController.current;
    val context = LocalContext.current;

    LaunchedEffect(key1 = Unit) {
        val user = googleAuthUiClient.getSignedInUser();

        if (user?.email != null && itemId != null) {
            getSingleItemFromDb(db, user.email, itemId) { item ->
                Log.d(ContentValues.TAG, "Items fetched $item")

                itemDetails = item;
            }
        }
    }

    Log.d(TAG, "Item details ::: $itemDetails");

    Column(
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 8.dp)
    ) {
        Surface(
            color = LightGrayColor,
            shape = RoundedCornerShape(bottomStart = 42.dp, bottomEnd = 42.dp),
            modifier = Modifier
                .wrapContentHeight(),
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 4.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackBtnClick,
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, BorderPrimaryColor),
                        modifier = Modifier.width(40.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.size(23.dp)
                        )
                    }

                    Text(
                        "Item Details",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FavoriteButton(onClick = { /*TODO*/ }, variants = FavButtonVariants.Default)

                        ThreeDotsDropdown(
                            onItemClick = { item ->
                                when (item) {
                                    "Edit" -> {
                                        val route = "${BabyBuyScreen.EditItem.name}/$itemId"
                                        navController.navigate(route)
                                    }

                                    "Delete" -> {
                                        val user = googleAuthUiClient.getSignedInUser();
                                            if (itemId != null && user?.email != null) {
                                                deleteItemFromDb(db, userId = user.email, itemId ){msg ->
                                                    Toast.makeText(
                                                        context,
                                                        msg,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        navController.navigate(BabyBuyScreen.Dashboard.name)
                                    }
                                }
                            }
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 28.dp, top = 8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(itemDetails?.get("picture")),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp)
                    )
                }


            }

        }

        /*
            Basic item information ================================
            =======================================================
         */
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = PrimaryColor,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.widthIn(min = 60.dp),
                content = {
                    Box(
                        contentAlignment = Alignment.Center  // Center the content
                    ) {
                        Text(
                            text = itemDetails?.get("category") as? String ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Color.White
                        )
                    }
                },
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = itemDetails?.get("name") as? String ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$ " + itemDetails?.get("price") as? String,
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryColor
                )
            }

            ViewRating(textFontSize = 13.sp, modifier = Modifier.size(16.dp))

            Text(
                itemDetails?.get("description") as? String ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = TextColor1,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )

        }


        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            /*
            Supporter information ===========================
            =================================================
             */
            Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
                Text(
                    text = "Supporter",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Name:", style = MaterialTheme.typography.labelMedium)
                    Text(
                        (itemDetails?.get("supporter") as? Map<*, *>)?.get("name") as? String ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextColor1
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Contact:", style = MaterialTheme.typography.labelMedium)
                    Text(
                        (itemDetails?.get("supporter") as? Map<*, *>)?.get("contact") as? String
                            ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextColor1
                    )
                }
            }

            /*
            Geotag information ===============================
            ==================================================
             */
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Geotag",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = itemDetails?.get("placeName") as? String ?: "",

                    style = MaterialTheme.typography.labelMedium,
                    color = TextColor1,
                    lineHeight = 20.sp
                )
            }
        }

        /*
            Google map box display =================================
            ========================================================
         */
        (itemDetails?.get("coordinates") as? Map<*, *>)?.let {
            GoogleMapBox(
                paddingValues = PaddingValues(horizontal = 16.dp),
                coordinates = LatLng(
                    it["latitude"] as? Double ?: 0.0,
                    it["longitude"] as? Double ?: 0.0
                ),
                showOnlyMarker = true
            )
        }

    }
}