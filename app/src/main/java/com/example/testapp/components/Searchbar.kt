package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
public fun SearchBar(searchInput: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = searchInput,
        onValueChange = onChange,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, Modifier.size(24.dp))
        },
        placeholder = {
            Text("Search here.." , style = MaterialTheme.typography.bodySmall)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(5.dp)
            )
            .height(52.dp)
            .fillMaxWidth(),
    )
}