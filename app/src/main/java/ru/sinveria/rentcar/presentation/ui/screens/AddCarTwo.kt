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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.sinveria.rentcar.R

@Composable
fun AddCarTwo(
    onNavigateBack: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    var year by rememberSaveable { mutableStateOf("") }
    var brand by rememberSaveable { mutableStateOf("") }
    var model by rememberSaveable { mutableStateOf("") }
    var transmission by rememberSaveable { mutableStateOf("") }
    var mileage by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    var showTransmissionMenu by rememberSaveable { mutableStateOf(false) }

    val transmissionOptions = listOf(
        stringResource(id = R.string.car_transmission_auto),
        stringResource(id = R.string.car_transmission_manual),
        stringResource(id = R.string.car_transmission_robot)
    )

    val isSubmitButtonEnabled = year.isNotEmpty() &&
            brand.isNotEmpty() &&
            model.isNotEmpty() &&
            transmission.isNotEmpty() &&
            mileage.isNotEmpty() &&
            description.isNotEmpty()

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
                text = stringResource(id = R.string.add_car_title),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.car_year_label),
            style = MaterialTheme.typography.labelLarge,
            color = colorResource(id = R.color.label_input),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = year,
                onValueChange = { year = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { /* ... */ },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (year.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.car_year_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.car_brand_label),
            style = MaterialTheme.typography.labelLarge,
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
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = brand,
                onValueChange = { brand = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { /* ... */ },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (brand.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.car_brand_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.car_model_hint),
            style = MaterialTheme.typography.labelLarge,
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
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = model,
                onValueChange = { model = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { /* ... */ },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (model.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.car_model_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.car_transmission_hint),
            style = MaterialTheme.typography.labelLarge,
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
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .clickable { showTransmissionMenu = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (transmission.isEmpty()) {
                        stringResource(id = R.string.car_transmission_hint)
                    } else {
                        transmission
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (transmission.isEmpty()) {
                        colorResource(id = R.color.input_text)
                    } else {
                        Color.Black
                    },
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = "Open dropdown",
                    modifier = Modifier
                        .size(42.dp)
                        .padding(end = 16.dp)
                )
            }

            DropdownMenu(
                expanded = showTransmissionMenu,
                onDismissRequest = { showTransmissionMenu = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                transmissionOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            transmission = option
                            showTransmissionMenu = false
                        }
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.car_mileage_hint),
            style = MaterialTheme.typography.labelLarge,
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
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = mileage,
                onValueChange = { mileage = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { /* ... */ },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (mileage.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.car_mileage_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.car_description_label),
            style = MaterialTheme.typography.labelLarge,
            color = colorResource(id = R.color.label_input),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
        ) {
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .onFocusChanged { /* ... */ },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                maxLines = 5,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (description.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.car_description_hint),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.input_text)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { if (isSubmitButtonEnabled) onSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSubmitButtonEnabled) {
                    colorResource(id = R.color.accent_color)
                } else {
                    colorResource(id = R.color.accent_color).copy(alpha = 0.2f)
                },
                contentColor = Color.White
            ),
            enabled = isSubmitButtonEnabled
        ) {
            Text(
                text = stringResource(id = R.string.car_submit_button),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}