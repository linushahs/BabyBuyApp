package com.example.testapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
public fun Topbar(modifier: Modifier = Modifier, color: Color){
    Surface( color = color) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 6.dp,
                    vertical = 12.dp
                ) ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "BabyBuy",
                style = MaterialTheme.typography.titleMedium
            )

            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", Modifier.size(22.dp))
        }
    }
}