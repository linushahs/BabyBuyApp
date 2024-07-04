package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.testapp.R;
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.TextFieldColor

@Composable
public fun EmailTextField(
    modifier: Modifier = Modifier,
    input: String,
    onChange: (String) -> Unit,
    borderColor: Color = BorderPrimaryColor,
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
            unfocusedContainerColor = TextFieldColor,
            focusedContainerColor = TextFieldColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(8.dp)
            )
            .height(52.dp)
            .fillMaxWidth(),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.email_sign),
                contentDescription = null,
                Modifier.size(25.dp),
                tint = Color.Gray
            )
        }
    )
}
