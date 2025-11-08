package ru.sinveria.rentcar.presentation.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import java.io.File
import coil.request.ImageRequest
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.presentation.navigation.Screen
import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation
import ru.sinveria.rentcar.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onCarDetailsClick: (Long) -> Unit = {},
    onBookCarClick: (Long) -> Unit = {},
    onHomeClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onSearchLoading: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val cars by viewModel.cars.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadCars()
    }

    var searchText by remember { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.light_purple))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 60.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 24.dp
                        ),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(12.dp),
                                clip = true
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = "Search",
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(20.dp)
                            )
                            BasicTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp, end = 16.dp)
                                    .onFocusChanged { focusState ->
                                        searchFocused = focusState.isFocused
                                    }
                                    .onKeyEvent { keyEvent ->
                                        if (keyEvent.key == Key.Enter) {
                                            if (searchText.isNotBlank()) {
                                                try {
                                                    onSearchLoading(searchText)
                                                } catch (e: Exception) {
                                                }
                                            }
                                            true
                                        } else {
                                            false
                                        }
                                    },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (searchText.isEmpty()) {
                                            Text(
                                                text = stringResource(R.string.search_hint),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = colorResource(id = R.color.input_text)
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }

                    Text(
                        text = stringResource(R.string.search_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White)
            ) {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    cars.isEmpty() -> {
                        EmptyCarsState()
                    }
                    else -> {
                        CarsList(
                            cars = cars,
                            onCarDetailsClick = onCarDetailsClick,
                            onBookCarClick = onBookCarClick
                        )
                    }
                }
            }
        }

        BottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            currentScreen = Screen.Home.route,
            onHomeClick = onHomeClick,
            onBookmarksClick = onBookmarksClick,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
private fun EmptyCarsState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Нет доступных автомобилей",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.input_text)
            )
            Text(
                text = "Добавьте свой первый автомобиль в профиле",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.input_text)
            )
        }
    }
}

@Composable
private fun CarsList(
    cars: List<CarEntity>,
    onCarDetailsClick: (Long) -> Unit,
    onBookCarClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cars) { car ->
            CarItemCard(
                car = car,
                onDetailsClick = { onCarDetailsClick(car.id) },
                onBookClick = { onBookCarClick(car.id) }
            )
        }
    }
}

@Composable
fun CarItemCard(
    car: CarEntity,
    onDetailsClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = car.model,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = car.brand,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = R.color.input_text)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "2500P",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.accent_color)
                            )
                        )
                        Text(
                            text = " в день",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.input_text)
                            ),
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }

                CarImage(car = car)
            }

            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpecificationChip(
                    iconRes = R.drawable.gearbox,
                    text = "А/Т"
                )

                Spacer(modifier = Modifier.width(8.dp))

                SpecificationChip(
                    iconRes = R.drawable.fuel,
                    text = "Бензин"
                )
            }

            CarActionButtons(
                onDetailsClick = onDetailsClick,
                onBookClick = onBookClick
            )
        }
    }
}

@Composable
fun CarImage(car: CarEntity) {
    val context = LocalContext.current

    if (car.photoUris.isNotEmpty()) {
        val firstPhotoUri = car.photoUris.split(",").firstOrNull()
        firstPhotoUri?.let { uriString ->
            val uri = remember(uriString) {
                try {
                    val parsedUri = Uri.parse(uriString)
                    if (parsedUri.scheme == "file") {
                        val file = File(parsedUri.path ?: "")
                        if (file.exists()) {
                            parsedUri
                        } else {
                            null
                        }
                    } else if (parsedUri.scheme == "content") {
                        parsedUri
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }

            if (uri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${car.brand} ${car.model}",
                    modifier = Modifier
                        .size(width = 120.dp, height = 90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )
            } else {
                DefaultCarImage()
            }
        } ?: run {
            DefaultCarImage()
        }
    } else {
        DefaultCarImage()
    }
}

@Composable
fun DefaultCarImage() {
    Image(
        painter = painterResource(id = R.drawable.machine),
        contentDescription = "Car image",
        modifier = Modifier
            .size(width = 120.dp, height = 90.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SpecificationChip(
    iconRes: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.label_input)
            )
        )
    }
}

@Composable
fun CarActionButtons(
    onDetailsClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onBookClick,
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent_color),
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = stringResource(R.string.book_button),
                maxLines = 1
            )
        }

        OutlinedButton(
            onClick = onDetailsClick,
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.accent_color)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                colorResource(id = R.color.accent_color)
            )
        ) {
            Text(
                text = stringResource(R.string.details_button),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}