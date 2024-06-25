package com.example.testapp.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.R
import com.example.testapp.components.FavButtonVariants
import com.example.testapp.components.FavoriteButton
import com.example.testapp.components.ViewRating
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.LightBgColor
import com.example.testapp.ui.theme.LightGrayColor
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.ui.theme.TextColor1
import com.example.testapp.ui.theme.TextColor2

@Preview(showBackground = true, widthDp = 370, heightDp = 700)
@Composable
fun ItemDetailsScreen(onBackBtnClick: () -> Unit = {}) {
    Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
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
                        .padding(10.dp)
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

                    FavoriteButton(onClick = { /*TODO*/ }, variants = FavButtonVariants.Default)
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.toy), contentDescription = null,
                        modifier = Modifier.size(160.dp)
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
                            text = "Toy",
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
                Text("Baby Car Toy", style = MaterialTheme.typography.titleMedium)
                Text("$ 40.05", style = MaterialTheme.typography.bodyMedium, color = PrimaryColor)
            }

            ViewRating(textFontSize = 13.sp, modifier = Modifier.size(16.dp))

            Text(
                "The store offers a wide range of products essential for baby care supplies. The store offers a wide range of products essential for baby care supplies.",
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
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /*
            Supporter information ===========================
            =================================================
             */
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Supporter",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Name:", style = MaterialTheme.typography.labelMedium)
                    Text(
                        "John Doe", style = MaterialTheme.typography.labelMedium,
                        color = TextColor1
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Contact:", style = MaterialTheme.typography.labelMedium)
                    Text(
                        "9811201293", style = MaterialTheme.typography.labelMedium,
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
                modifier = Modifier.width(140.dp)
            ) {
                Text(
                    text = "Geotag",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    "Baneshwor Area, Kathmandu", style = MaterialTheme.typography.labelMedium,
                    color = TextColor1,
                    lineHeight = 20.sp
                )
            }
        }
    }
}