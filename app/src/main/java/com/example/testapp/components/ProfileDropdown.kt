package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.RedColor
import com.example.testapp.ui.theme.TextColor1
import com.example.testapp.ui.theme.TextColor2
import com.example.testapp.R
import com.example.testapp.ui.theme.PrimaryColor

@Composable
fun ProfileDropdown(
    modifier: Modifier = Modifier,
    profilePictureUrl: String?,
    onLogoutClick: () -> Unit = { },
) {
    val items = listOf(
        mapOf("name" to "Logout", "icon" to R.drawable.logout),
    )
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        Surface(onClick = {expanded = true}, border = BorderStroke(0.5.dp, BorderPrimaryColor), shape = CircleShape) {
            if (profilePictureUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(profilePictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
        }

        MaterialTheme(
            shapes = Shapes(RoundedCornerShape(10.dp)),
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                offset = DpOffset(x = 0.dp, y = 5.dp),
                modifier = Modifier
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = BorderPrimaryColor,
                    )
                    .width(110.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    (item["icon"] as? Int)?.let {
                                        Icon(
                                            painter = painterResource(it),
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint =  RedColor
                                        )
                                    }
                                    Text(
                                        text = item["name"].toString(),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 13.sp,
                                        color = TextColor2
                                    )
                                }
                            },
                            onClick = {
                                onLogoutClick()
                                expanded = false
                            },
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                            modifier = Modifier.height(40.dp)
                        )
                        if (index < items.size - 1) {
                            HorizontalDivider(thickness = 0.5.dp, color = BorderPrimaryColor)
                        }
                    }
                }
            }
        }
    }
}