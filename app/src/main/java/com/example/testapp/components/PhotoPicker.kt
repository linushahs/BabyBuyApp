    package com.example.testapp.components;

    import android.Manifest
    import android.content.pm.PackageManager
    import android.net.Uri
    import android.util.Log
    import android.widget.Toast
    import androidx.activity.compose.rememberLauncherForActivityResult
    import androidx.activity.result.PickVisualMediaRequest
    import androidx.activity.result.contract.ActivityResultContracts
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Image
    import androidx.compose.material3.Button
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.ModalBottomSheet
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.material3.rememberModalBottomSheetState
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.core.content.ContextCompat
    import androidx.core.content.FileProvider
    import coil.compose.AsyncImage
    import coil.request.ImageRequest
    import com.example.testapp.ui.theme.BorderDarkColor
    import com.example.testapp.ui.theme.LightGrayColor
    import com.example.testapp.ui.theme.TextColor3
    import com.example.testapp.utils.createImageFile
    import com.example.testapp.utils.dashedBorder
    import java.util.Objects

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PhotoPicker(
        imageUri: Uri?,
        onImageSelected: (Uri?) -> Unit
    ) {
        var showBottomSheet by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val file by remember { mutableStateOf(context.createImageFile()) }
        val tempUri by remember {
            mutableStateOf(FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                "${context.packageName}.provider", file
            ))
        }

        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    onImageSelected(tempUri)
                }
            }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(tempUri)
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    onImageSelected(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        )

        Surface(
            onClick = { showBottomSheet = true },
            color = LightGrayColor,
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .dashedBorder(BorderDarkColor, RoundedCornerShape(9.dp), 1.dp)
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        tint = TextColor3
                    )
                    Spacer(modifier = Modifier.size(9.dp))
                    Text(
                        text = "Upload item picture",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColor3
                    )
                }
            }
        }

        if (showBottomSheet) {
            val sheetState = rememberModalBottomSheetState()
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Choose an option",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            showBottomSheet = false

                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(tempUri)
                            } else {
                                // Request a permission
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Take Photo")
                    }
                    Button(
                        onClick = {
                            showBottomSheet = false
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Choose from Gallery")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }


