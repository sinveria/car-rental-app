package ru.sinveria.rentcar.presentation.ui.screens

import android.Manifest
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.ui.components.ImageSourceDialog
import ru.sinveria.rentcar.presentation.viewmodel.SignUpThreeViewModel
import ru.sinveria.rentcar.utils.ImagePicker
import ru.sinveria.rentcar.utils.rememberCameraPermissionLauncher
import ru.sinveria.rentcar.utils.rememberGalleryPermissionLauncher
import ru.sinveria.rentcar.presentation.viewmodel.ImageType
import ru.sinveria.rentcar.utils.createImageUri
import ru.sinveria.rentcar.utils.getGalleryPermission
import ru.sinveria.rentcar.utils.hasCameraPermission
import ru.sinveria.rentcar.utils.hasGalleryPermission

@Composable
fun SignUpThree(
    onNavigateBack: () -> Unit = {},
    onNavigateToCong: () -> Unit = {},
    viewModel: SignUpThreeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val imagePicker = ImagePicker()

    val licenseNumberState by viewModel.licenseNumberState.collectAsState()
    val issueDateState by viewModel.issueDateState.collectAsState()
    val licenseNumberFocused by viewModel.licenseNumberFocused.collectAsState()
    val issueDateFocused by viewModel.issueDateFocused.collectAsState()
    val licenseNumberError by viewModel.licenseNumberError.collectAsState()
    val issueDateError by viewModel.issueDateError.collectAsState()
    val licenseNumberTouched by viewModel.licenseNumberTouched.collectAsState()
    val issueDateTouched by viewModel.issueDateTouched.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()
    val licenseImageUri by viewModel.licenseImageUri.collectAsState()
    val passportImageUri by viewModel.passportImageUri.collectAsState()
    val showImageSourceDialog by viewModel.showImageSourceDialog.collectAsState()
    val currentImageType by viewModel.currentImageType.collectAsState()

    val localCameraImageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = imagePicker.rememberCameraLauncher(
        cameraImageUri = localCameraImageUri,
        onImageCaptured = { uri ->
            viewModel.onImageCaptured(uri)
            localCameraImageUri.value = null
        }
    )

    val galleryLauncher = imagePicker.rememberGalleryLauncher { uri ->
        viewModel.onGalleryImageSelected(uri)
    }

    val cameraPermissionLauncher = rememberCameraPermissionLauncher(
        onPermissionGranted = {
            localCameraImageUri.value = createImageUri(context)
            localCameraImageUri.value?.let { uri ->
                cameraLauncher.launch(uri)
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
            .padding(24.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
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
                text = stringResource(id = R.string.create_account),
                fontSize = 20.sp,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.color_border),
                                shape = CircleShape
                            )
                            .clickable { viewModel.openImageSourceDialog(ImageType.PROFILE) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (profileImageUri != null) {
                            AsyncImage(
                                model = profileImageUri,
                                contentDescription = "Profile photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.profile_photo),
                                contentDescription = "Profile photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = "Add photo",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 3.dp, y = 75.dp)
                            .clickable { viewModel.openImageSourceDialog(ImageType.PROFILE) }
                    )
                }

                Text(
                    text = stringResource(id = R.string.signupthree_description),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.input_text),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.license_number),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 32.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = when {
                            licenseNumberTouched && licenseNumberError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            licenseNumberFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = licenseNumberState,
                    onValueChange = viewModel::onLicenseNumberChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onLicenseNumberFocusChanged(focusState.isFocused)
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (licenseNumberState.text.isEmpty()) {
                                Text(
                                    text = "0000000000",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (licenseNumberTouched && licenseNumberError.isNotEmpty()) {
                Text(
                    text = licenseNumberError,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.date_of_issue),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = when {
                            issueDateTouched && issueDateError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            issueDateFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(20.dp)
                    )
                    BasicTextField(
                        value = issueDateState,
                        onValueChange = viewModel::onIssueDateChange,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp, end = 16.dp)
                            .onFocusChanged { focusState ->
                                viewModel.onIssueDateFocusChanged(focusState.isFocused)
                            },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (issueDateState.text.isEmpty()) {
                                    Text(
                                        text = "DD/MM/YYYY",
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.input_text)
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            if (issueDateTouched && issueDateError.isNotEmpty()) {
                Text(
                    text = issueDateError,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.upload_lisence_photo),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { viewModel.openImageSourceDialog(ImageType.LICENSE) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.color_border),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (licenseImageUri != null) {
                        AsyncImage(
                            model = licenseImageUri,
                            contentDescription = "License photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.upload_icon),
                            contentDescription = "Upload license",
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.upload_photo),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.label_input),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.upload_passport_photo),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { viewModel.openImageSourceDialog(ImageType.PASSPORT) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.color_border),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (passportImageUri != null) {
                        AsyncImage(
                            model = passportImageUri,
                            contentDescription = "Passport photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.upload_icon),
                            contentDescription = "Upload passport",
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.upload_photo),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.label_input),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.markAllTouched()
                    if (viewModel.validateForm()) {
                        onNavigateToCong()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.accent_color),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}