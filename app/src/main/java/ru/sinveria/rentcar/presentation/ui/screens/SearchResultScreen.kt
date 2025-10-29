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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Composable
fun SearchResultsScreen(
    onBackClick: () -> Unit = {},
    onCarDetailsClick: (Int) -> Unit = {},
    onBookCarClick: (Int) -> Unit = {}
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.go_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
                    .align(Alignment.CenterStart)
            )
            Text(
                text = "Результаты поиска",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp),
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

        //BottomNavigation()
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

                    // Price section
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = car.price,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.accent_color)
                            )
                        )
                        Text(
                            text = car.pricePeriod,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = colorResource(id = R.color.input_text)
                            ),
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
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.label_input)
                        )
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
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.label_input)
                        )
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
                        text = "Забронировать",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
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
                        text = "Детали",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
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