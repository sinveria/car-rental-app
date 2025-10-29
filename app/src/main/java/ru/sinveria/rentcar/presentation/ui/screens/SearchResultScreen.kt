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
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.OutlinedButton
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import ru.sinveria.rentcar.R
    import ru.sinveria.rentcar.presentation.navigation.Screen
    import ru.sinveria.rentcar.presentation.ui.components.BottomNavigation

    @Composable
    fun SearchResultsScreen(
        onBackClick: () -> Unit = {},
        onCarDetailsClick: (Int) -> Unit = {},
        onBookCarClick: (Int) -> Unit = {},
        onHomeClick: () -> Unit = {},
        onSettingsClick: () -> Unit = {},
        onBookmarksClick: () -> Unit = {},
    ) {
        val carItems = listOf(
            CarItem(
                id = 1,
                name = "S 500 Sedan",
                brand = "Mercedes-Benz",
                price = "2500P",
                pricePeriod = "в день",
                transmission = "А/Т",
                fuelType = "Бензин",
                imageRes = R.drawable.machine
            ),
            CarItem(
                id = 2,
                name = "S 500 Sedan",
                brand = "Mercedes-Benz",
                price = "2500P",
                pricePeriod = "в день",
                transmission = "А/Т",
                fuelType = "Бензин",
                imageRes = R.drawable.machine
            ),
            CarItem(
                id = 3,
                name = "S 500 Sedan",
                brand = "Mercedes-Benz",
                price = "2500P",
                pricePeriod = "в день",
                transmission = "А/Т",
                fuelType = "Бензин",
                imageRes = R.drawable.machine
            )
        )

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
                        .padding(24.dp),
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
                        text = stringResource(id = R.string.search_results_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 72.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(carItems) { car ->
                        SearchResultCarItem(
                            car = car,
                            onDetailsClick = { onCarDetailsClick(car.id) },
                            onBookClick = { onBookCarClick(car.id) }
                        )
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
    fun SearchResultCarItem(
        car: CarItem,
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
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = car.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = car.brand,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.input_text),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        // Price section
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                text = car.price,
                                style = MaterialTheme.typography.bodyLarge,
                                color = colorResource(id = R.color.accent_color),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = car.pricePeriod,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text),
                                modifier = Modifier.padding(start = 4.dp, bottom = 1.dp)
                            )
                        }
                    }

                    Image(
                        painter = painterResource(id = car.imageRes),
                        contentDescription = "${car.brand} ${car.name}",
                        modifier = Modifier
                            .size(width = 176.dp, height = 136.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = car.transmission,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.label_input),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = " ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Transparent
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    // Fuel type
                    Column {
                        Text(
                            text = car.fuelType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.label_input),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = " ",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Transparent
                            )
                        )
                    }
                }

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
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.book_button),
                            style = MaterialTheme.typography.bodyMedium,
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
                            text = stringResource(id = R.string.details_button),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SearchResultsScreenPreview() {
        SearchResultsScreen()
    }