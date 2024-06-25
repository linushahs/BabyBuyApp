package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.LightBgColor
import com.example.testapp.ui.theme.PrimaryColor

enum class FavButtonVariants() {
    Outlined,
    Default
}

@Composable
fun FavoriteButton(
    onClick: () -> Unit,
    variants: FavButtonVariants = FavButtonVariants.Outlined,
    modifier: Modifier = Modifier
) {
    if (variants == FavButtonVariants.Outlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.size(35.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = LightBgColor
            ),
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, BorderPrimaryColor)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outlined_heart),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = PrimaryColor
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.size(35.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outlined_heart),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = PrimaryColor
            )
        }
    }
}