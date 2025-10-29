package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.navigation.Screen
import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation

@Composable
fun SearchResultsScreen(
    searchQuery: String,
    searchResults: List<CarItem>,
    onBackClick: () -> Unit = {},
    onCarDetailsClick: (Int) -> Unit = {},
    onBookCarClick: (Int) -> Unit = {},
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

            if (searchResults.isEmpty()) {
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
                        CarItemCard(
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