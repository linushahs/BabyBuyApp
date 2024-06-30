package com.example.testapp.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap;
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@Composable
fun GoogleMapBox(
    coordinates: LatLng = LatLng(27.7172, 85.3240),
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onLocationSelected: ((LatLng) -> Unit)? = null,
    showOnlyMarker: Boolean = false
) {
    val defaultLocation = coordinates // Default to Kathmandu
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
    }

    var isMapLoaded by remember { mutableStateOf(false) }
    var markerPosition by remember { mutableStateOf(coordinates) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(paddingValues)
            .clip(RoundedCornerShape(9.dp))
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                if (!showOnlyMarker && onLocationSelected != null) {
                    markerPosition = latLng
                    onLocationSelected(latLng)
                }
            },
            onMapLoaded = { isMapLoaded = true },
            properties = if (showOnlyMarker) {
                MapProperties(
                    isMyLocationEnabled = false,
                    mapType = MapType.NORMAL,
                )
            } else {
                MapProperties()
            }
        ) {
            Marker(
                state = MarkerState(position = markerPosition),
                title = "Selected Location"
            )
        }
    }
}

suspend fun getPlaceNameFromCoordinates(latLng: LatLng): String {
    val apiKey = "AIzaSyDxn7kIBwJOYXBf34Z3_dhfjJ8IBCVjGgY"
    val url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=${latLng.latitude},${latLng.longitude}&key=$apiKey"

    return withContext(Dispatchers.IO) {
        try {
            val response = URL(url).readText()
            val jsonObject = JSONObject(response)
            val results = jsonObject.getJSONArray("results")
            if (results.length() > 0) {
                results.getJSONObject(0).getString("formatted_address")
            } else {
                "Unknown Location"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}

