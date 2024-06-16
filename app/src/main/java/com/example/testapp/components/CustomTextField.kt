package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
public fun CustomTextField(
    modifier: Modifier = Modifier,
    input: String,
    onChange: (String) -> Unit,
    borderColor: Color = Color.LightGray,
    placeholder: String = ""
) {
    TextField(
        value = input,
        onValueChange = onChange,
        placeholder = {
            Text(placeholder, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
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
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(5.dp)
            )
            .height(52.dp)
            .fillMaxWidth(),
    )
}
