package ru.sinveria.rentcar.presentation.ui.screens

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.ui.components.ImageSourceDialog
import ru.sinveria.rentcar.presentation.viewmodel.AddCarPhotosViewModel
import ru.sinveria.rentcar.presentation.viewmodel.CarImageType
import ru.sinveria.rentcar.utils.ImagePicker
import ru.sinveria.rentcar.utils.createImageUri
import ru.sinveria.rentcar.utils.getGalleryPermission
import ru.sinveria.rentcar.utils.hasCameraPermission
import ru.sinveria.rentcar.utils.hasGalleryPermission
import ru.sinveria.rentcar.utils.rememberCameraPermissionLauncher
import ru.sinveria.rentcar.utils.rememberGalleryPermissionLauncher

@Composable
fun AddCarPhotos(
    onNavigateBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val viewModel: AddCarPhotosViewModel = hiltViewModel()
    val context = LocalContext.current
    val imagePicker = remember { ImagePicker() }

    val showImageSourceDialog by viewModel.showImageSourceDialog.collectAsState()
    val currentImageType by viewModel.currentImageType.collectAsState()
    val mainPhotoUri by viewModel.mainPhotoUri.collectAsState()
    val photo1Uri by viewModel.photo1Uri.collectAsState()
    val photo2Uri by viewModel.photo2Uri.collectAsState()
    val photo3Uri by viewModel.photo3Uri.collectAsState()
    val photo4Uri by viewModel.photo4Uri.collectAsState()
    val isNextEnabled by viewModel.isNextEnabled.collectAsState()

    val localCameraImageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = imagePicker.rememberCameraLauncher(
        cameraImageUri = localCameraImageUri,
        onImageCaptured = { uri ->
            viewModel.onImageCaptured(uri, context)
            localCameraImageUri.value = null
        }
    )
    val galleryLauncher = imagePicker.rememberGalleryLauncher { uri ->
        viewModel.onGalleryImageSelected(uri, context)
    }

    val cameraPermissionLauncher = rememberCameraPermissionLauncher(
        onPermissionGranted = {
            currentImageType?.let { type ->
                localCameraImageUri.value = createImageUri(context)
                localCameraImageUri.value?.let { uri ->
                    cameraLauncher.launch(uri)
                }
            }
        },
        onPermissionDenied = {
            viewModel.dismissImageSourceDialog()
            localCameraImageUri.value = null
        }
    )
    val galleryPermissionLauncher = rememberGalleryPermissionLauncher(
        onPermissionGranted = {
            currentImageType?.let { type ->
                galleryLauncher.launch("image/*")
            }
        },
        onPermissionDenied = {
            viewModel.dismissImageSourceDialog()
        }
    )

    fun handleCameraSelection() {
        if (hasCameraPermission(context)) {
            viewModel.handleCameraSelection()
            localCameraImageUri.value = createImageUri(context)
            localCameraImageUri.value?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun handleGallerySelection() {
        if (hasGalleryPermission(context)) {
            viewModel.handleGallerySelection()
            galleryLauncher.launch("image/*")
        } else {
            galleryPermissionLauncher.launch(getGalleryPermission())
        }
    }

    if (showImageSourceDialog) {
        ImageSourceDialog(
            onDismiss = { viewModel.dismissImageSourceDialog() },
            onCameraSelected = { handleCameraSelection() },
            onGallerySelected = { handleGallerySelection() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.go_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() }
            )
            Text(
                text = stringResource(id = R.string.add_car_photos_title),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(elevation = 4.dp)
                .background(Color.LightGray)
                .clickable { viewModel.openImageSourceDialog(CarImageType.CAR_MAIN) },
            contentAlignment = Alignment.Center
        ) {
            if (mainPhotoUri != null) {
                AsyncImage(
                    model = mainPhotoUri,
                    contentDescription = "Main photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            } else {
                Text("Передний вид", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { viewModel.openImageSourceDialog(CarImageType.CAR_SIDE1) },
                contentAlignment = Alignment.Center
            ) {
                if (photo1Uri != null) {
                    AsyncImage(
                        model = photo1Uri,
                        contentDescription = "Side photo 1",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Text("Левый бок", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { viewModel.openImageSourceDialog(CarImageType.CAR_SIDE2) },
                contentAlignment = Alignment.Center
            ) {
                if (photo2Uri != null) {
                    AsyncImage(
                        model = photo2Uri,
                        contentDescription = "Side photo 2",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Text("Правый бок", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { viewModel.openImageSourceDialog(CarImageType.CAR_INTERIOR) },
                contentAlignment = Alignment.Center
            ) {
                if (photo3Uri != null) {
                    AsyncImage(
                        model = photo3Uri,
                        contentDescription = "Interior",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Text("Салон", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { viewModel.openImageSourceDialog(CarImageType.CAR_REAR) },
                contentAlignment = Alignment.Center
            ) {
                if (photo4Uri != null) {
                    AsyncImage(
                        model = photo4Uri,
                        contentDescription = "Rear view",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    Text("Задний вид", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            enabled = isNextEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent_color),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.next),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}