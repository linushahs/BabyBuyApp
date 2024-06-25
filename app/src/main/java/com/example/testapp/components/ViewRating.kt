package com.example.testapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.YellowColor

@Composable
fun ViewRating( modifier: Modifier = Modifier, rating: String = "4.5", textFontSize: TextUnit = 11.sp) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = YellowColor,
            modifier = modifier.size(14.dp)
        )

        Text(
            text = rating,
            style = MaterialTheme.typography.labelSmall,
            color = YellowColor,
            fontSize = textFontSize
        )
    }
}