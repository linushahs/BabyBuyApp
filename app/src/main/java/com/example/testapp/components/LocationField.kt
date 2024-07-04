package com.example.testapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.utils.GoogleMapBox
import com.google.android.gms.maps.model.LatLng

@Composable
fun LocationField(
    selectedLocation: LatLng? = null,
    placeName: String,
    onLocationSelected: ((LatLng) -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Location", style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 3.dp),
        )
        Surface(
            content = {
                Text(
                    text = placeName , style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
                    lineHeight = 18.sp
                )
            },
            border = BorderStroke(1.dp, BorderPrimaryColor),
            shape = RoundedCornerShape(6.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        )
        GoogleMapBox(
            coordinates = selectedLocation ?: LatLng(27.7172, 85.3240),
            paddingValues = PaddingValues(0.dp),
            onLocationSelected = onLocationSelected
        )
    }
}