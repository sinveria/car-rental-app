package ru.sinveria.rentcar.presentation.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.presentation.navigation.Screen
import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation
import ru.sinveria.rentcar.presentation.viewmodel.UserProfileViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLastRegisteredUser()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
            } else {
                UserProfileContent(
                    userEntity = userData,
                    onNavigateBack = onNavigateBack,
                    onLogoutClick = onLogoutClick
                )
            }
        }

        BottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            currentScreen = Screen.UserProfile.route,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
fun UserProfileContent(
    userEntity: UserEntity?,
    onNavigateBack: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            ProfilePhotoSection(userEntity = userEntity)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileInfoSection(userEntity = userEntity)

            Spacer(modifier = Modifier.height(32.dp))

            LogoutButton(onClick = onLogoutClick)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfilePhotoSection(userEntity: UserEntity?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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
            ) {
                if (!userEntity?.profilePhotoPath.isNullOrEmpty()) {
                    LoadImageFromUriProfile(
                        uri = userEntity?.profilePhotoPath ?: "",
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.profile_photo),
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Add photo",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${userEntity?.firstName ?: ""} ${userEntity?.lastName ?: ""}".trim().ifEmpty { "Пользователь" },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Присоединился ${getJoinDate()}",
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.input_text),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun ProfileInfoSection(userEntity: UserEntity?) {
    Column {
        ProfileInfoItem(
            title = "Электронная почта",
            value = userEntity?.email ?: "Не указано",
            showDivider = true
        )

        ProfileInfoItem(
            title = "Пароль",
            value = "Поменять пароль",
            valueColor = colorResource(id = R.color.accent_color),
            showDivider = true
        )

        ProfileInfoItem(
            title = "Пол",
            value = userEntity?.gender ?: "Не указано",
            showDivider = true
        )

        ProfileInfoItem(
            title = "Google",
            value = userEntity?.email ?: "Не подключено",
            showDivider = false
        )
    }
}

@Composable
fun ProfileInfoItem(
    title: String,
    value: String,
    valueColor: Color = Color.Black,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.input_text)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = valueColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        if (showDivider) {
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
        }
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(id = R.color.color_border))
    )
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Выйти из профиля",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.accent_color),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LoadImageFromUriProfile(
    uri: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uri) {
        try {
            val bitmap = if (uri.startsWith("content://")) {
                loadImageBitmapFromContentUri(context, uri)
            } else {
                loadImageBitmapFromFilePath(uri)
            }

            if (bitmap != null) {
                imageBitmap = bitmap
            } else {
                errorMessage = "Failed to load image"
            }
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }
    } else if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap!!,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

private fun loadImageBitmapFromContentUri(context: Context, uriString: String): ImageBitmap? {
    return try {
        val uri = Uri.parse(uriString)
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val bitmap = BitmapFactory.decodeStream(stream)
            bitmap?.asImageBitmap()
        }
    } catch (e: Exception) {
        null
    }
}

private fun loadImageBitmapFromFilePath(filePath: String): ImageBitmap? {
    return try {
        val file = File(filePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(filePath)
            bitmap?.asImageBitmap()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

private fun getJoinDate(): String {
    val dateFormat = SimpleDateFormat("MMMM yyyy", Locale("ru"))
    return dateFormat.format(Date())
}