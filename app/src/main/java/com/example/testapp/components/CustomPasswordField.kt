package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.TextFieldColor
import com.example.testapp.utils.PasswordValidationMessage

@Composable
public fun CustomPasswordField(
    modifier: Modifier = Modifier,
    input: String,
    passwordVisible: Boolean,
    togglePasswordVisibility: () -> Unit,
    onChange: (String) -> Unit,
    borderColor: Color = BorderPrimaryColor,
    placeholder: String = "",
) {
    TextField(
        value = input,
        onValueChange = onChange,
        placeholder = {
            Text(placeholder, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = togglePasswordVisibility) {
                Icon(imageVector = image, description, tint = Color.Gray)
            }
        },
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
    )
}


@Preview
@Composable
fun PasswordRulesContainer(password: String = "hello") {
    Surface(
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.Red),
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text("Password must contain:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            );

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                PasswordRule(
                    ruleSatisfied = password.any { it.isLowerCase() },
                    ruleText = PasswordValidationMessage.AtLeastOneLowercase.message
                )
                PasswordRule(
                    ruleSatisfied = password.any { it.isUpperCase() },
                    ruleText = PasswordValidationMessage.AtLeastOneUppercase.message
                )
                PasswordRule(
                    ruleSatisfied = password.any { it.isDigit() },
                    ruleText = PasswordValidationMessage.AtLeastOneNumeric.message
                )
                PasswordRule(
                    ruleSatisfied = password.none { it.isWhitespace() },
                    ruleText = PasswordValidationMessage.NoWhitespace.message
                )
                PasswordRule(
                    ruleSatisfied = password.length >= 6,
                    ruleText = PasswordValidationMessage.MinLength.message
                )
            }
        }
    }
}

@Composable
fun PasswordRule(ruleSatisfied: Boolean, ruleText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(
                    color = if (ruleSatisfied) Color.DarkGray else Color.Red
                )
        )
        Text(
            text = ruleText,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            color = if (ruleSatisfied) Color.DarkGray else Color.Red
        )
    }
}
