package ru.sinveria.rentcar.presentation.ui.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.presentation.navigation.Screen
import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation

@Composable
fun SearchResultsScreen(
    searchQuery: String,
    searchResults: List<CarEntity>,
    isLoading: Boolean = false,
    onBackClick: () -> Unit = {},
    onCarDetailsClick: (Long) -> Unit = {},
    onBookCarClick: (Long) -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.go_back),
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() }
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "${stringResource(id = R.string.search_results_title)}: $searchQuery",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (searchResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "По запросу \"$searchQuery\" ничего не найдено",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 72.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchResults) { car ->
                        SearchCarItemCard(
                            car = car,
                            onDetailsClick = { onCarDetailsClick(car.id) },
                            onBookClick = { onBookCarClick(car.id) }
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
fun SearchCarItemCard(
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

                SearchCarImage(car = car)
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
private fun SearchCarImage(car: CarEntity) {
    if (car.photoUris.isNotEmpty()) {
        val firstPhotoUri = car.photoUris.split(",").firstOrNull()
        firstPhotoUri?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "${car.brand} ${car.model}",
                modifier = Modifier
                    .size(width = 120.dp, height = 90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.machine),
                error = painterResource(id = R.drawable.machine)
            )
        } ?: run {
            DefaultCarImage()
        }
    } else {
        DefaultCarImage()
    }
}