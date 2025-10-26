package ru.sinveria.rentcar.presentation.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.presentation.viewmodel.UserProfileViewModel
import java.io.File

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLastRegisteredUser()
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        UserProfileContent(
            userEntity = userData,
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
fun UserProfileContent(
    userEntity: UserEntity?,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Профиль пользователя",
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(id = R.color.accent_color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Личные данные
        Text(
            text = "Личные данные:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InfoRow("Email:", userEntity?.email ?: "Не указано")
        InfoRow("Имя:", userEntity?.firstName ?: "Не указано")
        InfoRow("Фамилия:", userEntity?.lastName ?: "Не указано")
        InfoRow("Отчество:", userEntity?.middleName ?: "Не указано")
        InfoRow("Дата рождения:", userEntity?.birthDate ?: "Не указано")
        InfoRow("Пол:", userEntity?.gender ?: "Не указано")

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Водительское удостоверение:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InfoRow("Номер удостоверения:", userEntity?.licenseNumber ?: "Не указано")
        InfoRow("Дата выдачи:", userEntity?.licenseIssueDate ?: "Не указано")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Загруженные фото:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PhotoItem(
                label = "Фото профиля",
                imagePath = userEntity?.profilePhotoPath
            )
            PhotoItem(
                label = "Фото прав",
                imagePath = userEntity?.licensePhotoPath
            )
            PhotoItem(
                label = "Фото паспорта",
                imagePath = userEntity?.passportPhotoPath
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Статус сохранения:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (userEntity != null) {
            Text(
                text = "✅ Данные успешно сохранены в локальную базу данных Room",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "🔐 Токен авторизации сгенерирован и сохранен",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "📱 Вы можете войти в приложение с этими данными",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(
                text = "❌ Данные пользователя не найдены в базе данных",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.error_color)
            )
        }
    }
}

@Composable
fun PhotoItem(label: String, imagePath: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (!imagePath.isNullOrEmpty()) {
            LoadImageFromUri(
                uri = imagePath,
                contentDescription = label,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Не загружено",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun LoadImageFromUri(
    uri: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uri) {
        println("🖼️ DEBUG: Loading image from: $uri")
        try {
            val bitmap = if (uri.startsWith("content://")) {
                // Загрузка из content URI (временные файлы)
                loadImageBitmapFromContentUri(context, uri)
            } else {
                // Загрузка из файлового пути (постоянные файлы)
                loadImageBitmapFromFilePath(uri)
            }

            if (bitmap != null) {
                imageBitmap = bitmap
                println("✅ DEBUG: Image loaded successfully")
            } else {
                errorMessage = "Failed to load image"
                println("❌ DEBUG: Failed to load image from: $uri")
            }
        } catch (e: Exception) {
            errorMessage = e.message
            println("💥 DEBUG: Error loading image: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    // ДОБАВЬТЕ ЭТУ ЛОГИКУ ОТОБРАЖЕНИЯ:
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Ошибка загрузки",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
                Text(
                    text = errorMessage ?: "Неизвестная ошибка",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// Функция для загрузки из content URI
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

// Функция для загрузки из файлового пути
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

// Функция для загрузки bitmap из URI
private fun loadImageBitmapFromUri(context: Context, uriString: String): ImageBitmap? {
    return try {
        println("🔍 DEBUG: Parsing URI: $uriString")
        val uri = Uri.parse(uriString)

        // Проверяем доступность URI
        val inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream == null) {
            println("❌ DEBUG: Cannot open input stream for URI: $uri")
            return null
        }

        inputStream.use { stream ->
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = false
                inSampleSize = 2 // Уменьшаем размер для оптимизации
            }

            val bitmap = BitmapFactory.decodeStream(stream, null, options)
            if (bitmap == null) {
                println("❌ DEBUG: BitmapFactory returned null")
                return null
            }

            println("✅ DEBUG: Bitmap loaded - width: ${bitmap.width}, height: ${bitmap.height}")
            bitmap.asImageBitmap()
        }
    } catch (e: SecurityException) {
        println("🔐 DEBUG: Security exception - permission denied for URI: $uriString")
        null
    } catch (e: Exception) {
        println("💥 DEBUG: Exception loading image: ${e.message}")
        e.printStackTrace()
        null
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = if (value.isNotEmpty()) value else "Не указано",
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.input_text)
        )
    }
}