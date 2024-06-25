package com.example.testapp.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.R
import com.example.testapp.ui.theme.BabyBuyAppTheme
import com.example.testapp.ui.theme.GradientColor1
import com.example.testapp.ui.theme.GradientColor2
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.ui.theme.TextColor1
import com.example.testapp.ui.theme.fontFamily

@Preview(showBackground = true, widthDp = 360, heightDp = 812)
@Composable
fun GetStartedScreenPreview(onGetStartedClicked: () -> Unit = {}) {
    BabyBuyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.getstarted_bg),
                contentDescription = "background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.6F
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                ) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(28.dp))) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "logo",
                            modifier = Modifier.size(140.dp),
                            )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 14.dp)
                    ) {
                        Text(
                            text = "Manage",
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(GradientColor1, GradientColor2)
                                ),
                                fontFamily = fontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )

                        Text(
                            text = "All Your Baby Stuffs",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }


                    Text(
                        text = "The store offers a wide range of products essential for baby care supplies.",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextColor1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Button(
                        onClick = onGetStartedClicked,
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Get Started",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowRightAlt,
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.BottomEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}