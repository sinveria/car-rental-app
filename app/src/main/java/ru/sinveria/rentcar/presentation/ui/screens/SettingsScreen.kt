package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.presentation.navigation.Screen
import ru.sinveria.rentcar.presentation.viewmodel.UserProfileViewModel
import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit = {},
    onBookingsClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onConnectCarClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onInviteFriendClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SettingsContent(
                    userEntity = userData,
                    onNavigateBack = onNavigateBack,
                    onBookingsClick = onBookingsClick,
                    onThemeClick = onThemeClick,
                    onNotificationsClick = onNotificationsClick,
                    onConnectCarClick = onConnectCarClick,
                    onHelpClick = onHelpClick,
                    onInviteFriendClick = onInviteFriendClick,
                    onProfileClick = onProfileClick
                )
            }
        }

        BottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            currentScreen = Screen.Settings.route,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
fun SettingsContent(
    userEntity: UserEntity?,
    onNavigateBack: () -> Unit,
    onBookingsClick: () -> Unit,
    onThemeClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onConnectCarClick: () -> Unit,
    onHelpClick: () -> Unit,
    onInviteFriendClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.go_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() }
                    .align(Alignment.CenterStart)
            )

            Text(
                text = "Настройки",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProfileClick() }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserProfileSection(userEntity = userEntity)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Settings items
            SettingsItem(
                iconRes = R.drawable.bookmark,
                text = "Мои бронирования",
                onClick = onBookingsClick
            )

            SettingsItem(
                iconRes = R.drawable.theme_icon,
                text = "Тема",
                onClick = onThemeClick
            )

            SettingsItem(
                iconRes = R.drawable.notice,
                text = "Уведомления",
                onClick = onNotificationsClick
            )

            SettingsItem(
                iconRes = R.drawable.banknotes,
                text = "Подключить свой автомобиль",
                onClick = onConnectCarClick
            )

            SettingsItem(
                iconRes = R.drawable.help,
                text = "Помощь",
                onClick = onHelpClick
            )

            SettingsItem(
                iconRes = R.drawable.letter,
                text = "Пригласи друга",
                onClick = onInviteFriendClick
            )
        }
    }
}

@Composable
fun UserProfileSection(userEntity: UserEntity?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile photo
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        ) {
            if (!userEntity?.profilePhotoPath.isNullOrEmpty()) {
                LoadImageFromUri(
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

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "${userEntity?.firstName ?: ""} ${userEntity?.lastName ?: ""}".trim().ifEmpty { "Пользователь" },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = userEntity?.email ?: "Не указано",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.input_text),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun LoadImageFromUri(uri: String, contentDescription: String, modifier: Modifier) {
    LoadImageFromUriSettings(
        uri = uri,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

@Composable
fun SettingsItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Image(
            painter = painterResource(id = R.drawable.go_next),
            contentDescription = "Navigate",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun LoadImageFromUriSettings(
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

private fun loadImageBitmapFromContentUri(context: android.content.Context, uriString: String): ImageBitmap? {
    return try {
        val uri = android.net.Uri.parse(uriString)
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
            bitmap?.asImageBitmap()
        }
    } catch (e: Exception) {
        null
    }
}

private fun loadImageBitmapFromFilePath(filePath: String): ImageBitmap? {
    return try {
        val file = java.io.File(filePath)
        if (file.exists()) {
            val bitmap = android.graphics.BitmapFactory.decodeFile(filePath)
            bitmap?.asImageBitmap()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}