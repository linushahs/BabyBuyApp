package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.BorderSecondaryColor
import com.example.testapp.ui.theme.PrimaryColor

@Preview
@Composable
fun CategoryRow() {
    val categories = listOf("All Items", "Toys", "Dress", "Furniture", "Miscellaneous")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            Button(
                onClick = { selectedCategory = category },
                contentPadding = PaddingValues(vertical = 2.dp, horizontal = 11.dp),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.height(32.dp),
                border = BorderStroke(
                    1.dp,
                    if (isSelected) BorderSecondaryColor else BorderPrimaryColor
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) PrimaryColor else Color.Transparent
                )
            ) {
                Text(
                    category,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}