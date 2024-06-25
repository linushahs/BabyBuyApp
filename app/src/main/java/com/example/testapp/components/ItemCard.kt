package com.example.testapp.components

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.testapp.BabyBuyScreen
import com.example.testapp.LocalNavController
import com.example.testapp.R
import com.example.testapp.ui.theme.LightPrimaryColor
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.ui.theme.TextColor1

@Preview(showBackground = true, widthDp = 200, heightDp = 260)
@Composable
fun ItemCard(modifier: Modifier = Modifier, item: Map<String, Any>? = null) {
    val navController = LocalNavController.current;

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = { navController.navigate(BabyBuyScreen.ItemDetails.name) }
    ) {
        Column {
            ImageCardSection(item?.get("picture") as? String ?: "")

            Column(
                modifier = Modifier.padding(top = 6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item?.get("name") as? String ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    )

                    ViewRating()
                }

                Text(text = "$" + item?.get("price") as? String, style = MaterialTheme.typography.labelSmall, color = TextColor1)
            }
        }
    }
}

@Composable
private fun ImageCardSection(pictureUri: String, modifier: Modifier = Modifier, ) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .height(160.dp),
    ) {
        Surface(shape = RoundedCornerShape(10.dp), color = LightPrimaryColor) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(pictureUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            FavoriteButton(
                onClick = { /*TODO*/ },
                variants = FavButtonVariants.Outlined,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(55.dp)
                    .padding(10.dp)
            )
        }
    }
}