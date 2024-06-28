package com.example.testapp.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.LocalGoogleAuthUiClient
import com.example.testapp.ui.theme.BabyBuyAppTheme
import com.example.testapp.R;
import com.example.testapp.auth.FirebaseAuthClient
import com.example.testapp.auth.SignInViewModel
import com.example.testapp.auth.UserData
import com.example.testapp.components.CategoryRow
import com.example.testapp.components.FavButtonVariants
import com.example.testapp.components.FavoriteButton
import com.example.testapp.components.ItemCard
import com.example.testapp.components.ItemsGrid
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.components.SearchBar
import com.example.testapp.components.Topbar
import com.example.testapp.components.ViewRating
import com.example.testapp.ui.theme.BorderSecondaryColor
import com.example.testapp.ui.theme.LightBgColor
import com.example.testapp.ui.theme.TextColor1
import com.example.testapp.utils.getItemsFromDb
import com.google.firebase.firestore.FirebaseFirestore


//@Preview(showBackground = true, widthDp = 370, heightDp = 700)
@Composable
fun DashboardScreen(
    onAddItemClick: () -> Unit = {},
) {
    val db = FirebaseFirestore.getInstance();
    val googleAuthUiClient = LocalGoogleAuthUiClient.current;

    var searchInput by remember { mutableStateOf("") }
    val listOfItems = remember { mutableStateListOf<Map<String, Any>>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        isLoading = true
        val user = googleAuthUiClient.getSignedInUser();

        if (user?.email != null) {
            getItemsFromDb(db, user.email) { items ->
                Log.d(TAG, "Items fetched $items")
                listOfItems.clear()
                listOfItems.addAll(items)
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = PrimaryColor, modifier = Modifier.size(42.dp))
        }
    } else {
        BabyBuyAppTheme {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /* Topbar profile and notifications options =================
             ========================================================== */
                Topbar(googleAuthUiClient)

                /*
                Search bar
                ================================
            */
                SearchBar(searchInput = searchInput, onChange = { it -> searchInput = it })

                /*
                Highlighted card and add item section
                ======================================
             */
                Card(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(9.dp),
                ) {
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.card_bg),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 22.dp, start = 70.dp)
                        ) {
                            Text(
                                text = "Baby Items",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )

                            Text(
                                text = "The store offers variety of products, items.",
                                style = MaterialTheme.typography.labelSmall,
                                color = LightBgColor,
                                modifier = Modifier.width(180.dp)
                            )

                            Row(
                                modifier = Modifier.padding(top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(18.dp)
                            ) {
                                Text(
                                    text = "$ 40.05",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,

                                    )

                                /*
                                View Rating ================
                             */
                                ViewRating()
                            }
                        }

                        Button(
                            onClick = onAddItemClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .width(115.dp)
                                .height(46.dp)
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus_icon),
                                tint = PrimaryColor,
                                modifier = Modifier.size(16.dp),
                                contentDescription = null
                            )

                            Text(
                                "Add Item",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }
                }

                /*
                List of categories (Scrollable)
                # CategoryRow: Composable
                ================================
            */
                CategoryRow()

                /*
                 List of cards (Scrollable)
                 # ItemCard: Composable
                 ================================
             */
                if (listOfItems.isEmpty()) {
                    Text(
                        "No items available",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextColor1
                    )
                } else {
                    ItemsGrid(listOfItems)
                }
            }

        }
    }

}


