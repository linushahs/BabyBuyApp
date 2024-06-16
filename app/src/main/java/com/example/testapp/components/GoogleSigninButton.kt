package com.example.testapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testapp.R

@Composable
fun GoogleSigninButton(onBtnClicked: () -> Unit = {} ){
    OutlinedButton(
        onClick = onBtnClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .height(45.dp),
        shape = RoundedCornerShape(7.dp),

        ) {
        Icon(
            painter = painterResource(id = R.drawable.google),
            contentDescription = null,
            tint = Color.Unspecified
        );
        Text(
            "Signin with google",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 12.dp),
            color = Color.DarkGray
        )
    }
}