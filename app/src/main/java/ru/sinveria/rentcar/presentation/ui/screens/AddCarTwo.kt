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
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.viewmodel.AddCarSharedViewModel
import ru.sinveria.rentcar.presentation.viewmodel.AddCarTwoViewModel

@Composable
fun AddCarTwo(
    onNavigateBack: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    val viewModel: AddCarTwoViewModel = hiltViewModel()
    val sharedVm: AddCarSharedViewModel = hiltViewModel()

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
                value = viewModel.year,
                onValueChange = { viewModel.onYearChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { focusState ->
                        viewModel.onYearFocusChanged(focusState.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (viewModel.year.isEmpty()) {
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

        if (viewModel.yearTouched && viewModel.yearError.isNotEmpty()) {
            Text(
                text = viewModel.yearError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
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
                value = viewModel.brand,
                onValueChange = { viewModel.onBrandChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { focusState ->
                        viewModel.onBrandFocusChanged(focusState.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (viewModel.brand.isEmpty()) {
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

        if (viewModel.brandTouched && viewModel.brandError.isNotEmpty()) {
            Text(
                text = viewModel.brandError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
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
                value = viewModel.model,
                onValueChange = { viewModel.onModelChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { focusState ->
                        viewModel.onModelFocusChanged(focusState.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (viewModel.model.isEmpty()) {
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

        if (viewModel.modelTouched && viewModel.modelError.isNotEmpty()) {
            Text(
                text = viewModel.modelError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
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
                    color = if (viewModel.transmissionError.isNotEmpty() && viewModel.transmissionTouched) {
                        colorResource(id = R.color.error_color)
                    } else {
                        colorResource(id = R.color.color_border)
                    },
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
                    text = if (viewModel.transmission.isEmpty()) {
                        stringResource(id = R.string.car_transmission_hint)
                    } else {
                        viewModel.transmission
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (viewModel.transmission.isEmpty()) {
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
                            viewModel.transmission = option
                            showTransmissionMenu = false
                        }
                    )
                }
            }
        }

        if (viewModel.transmissionError.isNotEmpty() && viewModel.transmissionTouched) {
            Text(
                text = viewModel.transmissionError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
            )
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
                value = viewModel.mileage,
                onValueChange = { viewModel.onMileageChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { focusState ->
                        viewModel.onMileageFocusChanged(focusState.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (viewModel.mileage.isEmpty()) {
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

        if (viewModel.mileageTouched && viewModel.mileageError.isNotEmpty()) {
            Text(
                text = viewModel.mileageError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
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
                value = viewModel.description,
                onValueChange = { viewModel.onDescriptionChange(it) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .onFocusChanged { focusState ->
                        viewModel.onDescriptionFocusChanged(focusState.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                maxLines = 5,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        if (viewModel.description.isEmpty()) {
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

        if (viewModel.descriptionTouched && viewModel.descriptionError.isNotEmpty()) {
            Text(
                text = viewModel.descriptionError,
                color = colorResource(id = R.color.error_color),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.onSubmit()
                if (viewModel.isSubmitEnabled) {
                    onSubmit()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (viewModel.isSubmitEnabled) {
                    colorResource(id = R.color.accent_color)
                } else {
                    colorResource(id = R.color.accent_color).copy(alpha = 0.2f)
                },
                contentColor = Color.White
            ),
            enabled = viewModel.isSubmitEnabled
        ) {
            Text(
                text = stringResource(id = R.string.car_submit_button),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}