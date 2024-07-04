package com.example.testapp.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.BabyBuyScreen
import com.example.testapp.LocalNavController
import com.example.testapp.R
import com.example.testapp.auth.FirebaseAuthClient
import com.example.testapp.auth.SignInViewModel
import com.example.testapp.auth.UserData
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.LightBgColor
import kotlinx.coroutines.launch

@Composable
public fun Topbar(googleAuthClient: FirebaseAuthClient, modifier: Modifier = Modifier){
    val userData = googleAuthClient.getSignedInUser();
    val coroutineScope = rememberCoroutineScope();
    val navController = LocalNavController.current;
    Log.d("User", userData.toString());

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            ProfileDropdown(profilePictureUrl = userData?.profilePictureUrl){
                coroutineScope.launch {
                    googleAuthClient.signOut();
                    navController.navigate(BabyBuyScreen.Login.name)
                }
            }
            
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Hello",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if(userData?.username != null && userData.username != "") userData.username else "User 1",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FavoriteButton(onClick = {}, variants = FavButtonVariants.Outlined)

            OutlinedButton(
                onClick = { },
                modifier = Modifier.size(35.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = LightBgColor
                ),
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, BorderPrimaryColor)
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    modifier = Modifier.size(22.dp),
                    contentDescription = null
                )
            }
        }
    }
}