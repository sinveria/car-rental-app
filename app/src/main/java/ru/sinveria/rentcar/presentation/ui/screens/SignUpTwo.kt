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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.viewmodel.SignUpTwoViewModel
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun SignUpTwo(
    onNavigateBack: () -> Unit = {},
    onNavigateToSignUpThree: () -> Unit = {},
    viewModel: SignUpTwoViewModel = hiltViewModel()
) {
    val lastName by viewModel.lastName.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val middleName by viewModel.middleName.collectAsState()
    val birthDateState by viewModel.birthDateState.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()
    val lastNameFocused by viewModel.lastNameFocused.collectAsState()
    val firstNameFocused by viewModel.firstNameFocused.collectAsState()
    val middleNameFocused by viewModel.middleNameFocused.collectAsState()
    val birthDateFocused by viewModel.birthDateFocused.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val firstNameError by viewModel.firstNameError.collectAsState()
    val birthDateError by viewModel.birthDateError.collectAsState()
    val genderError by viewModel.genderError.collectAsState()
    val lastNameTouched by viewModel.lastNameTouched.collectAsState()
    val firstNameTouched by viewModel.firstNameTouched.collectAsState()
    val birthDateTouched by viewModel.birthDateTouched.collectAsState()
    val genderTouched by viewModel.genderTouched.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()

    if (showDatePicker) {
        Material3DatePickerDialog(
            onDismissRequest = { viewModel.onDismissDatePicker() },
            onDateSelected = { date ->
                viewModel.onDateSelected(date.year, date.monthNumber - 1, date.dayOfMonth)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 87.dp),
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
                text = stringResource(id = R.string.create_account),
                fontSize = 20.sp,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.surname),
                fontSize = 14.sp,
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
                        color = when {
                            lastNameTouched && lastNameError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            lastNameFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = lastName,
                    onValueChange = viewModel::onLastNameChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onLastNameFocusChanged(focusState.isFocused)
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
                            if (lastName.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_surname),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (lastNameTouched && lastNameError.isNotEmpty()) {
                Text(
                    text = lastNameError,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.name),
                fontSize = 14.sp,
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
                        color = when {
                            firstNameTouched && firstNameError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            firstNameFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = firstName,
                    onValueChange = viewModel::onFirstNameChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onFirstNameFocusChanged(focusState.isFocused)
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
                            if (firstName.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_name),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (firstNameTouched && firstNameError.isNotEmpty()) {
                Text(
                    text = firstNameError,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.patronymic),
                fontSize = 14.sp,
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
                        color = if (middleNameFocused) colorResource(id = R.color.accent_color)
                        else colorResource(id = R.color.color_border),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = middleName,
                    onValueChange = viewModel::onMiddleNameChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
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
                            if (middleName.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_patronymic),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Text(
                text = stringResource(id = R.string.birthdate),
                fontSize = 14.sp,
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
                        color = when {
                            birthDateTouched && birthDateError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            birthDateFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(start = 10.dp)
                            .size(20.dp)
                            .clickable { viewModel.onShowDatePicker() }
                    )

                    BasicTextField(
                        value = birthDateState,
                        onValueChange = viewModel::onBirthDateChange,
                        modifier = Modifier
                            .weight(1f),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (birthDateState.text.isEmpty()) {
                                    Text(
                                        text = "DD/MM/YYYY",
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.input_text)
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            if (birthDateTouched && birthDateError.isNotEmpty()) {
                Text(
                    text = birthDateError,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Column {
                Text(
                    text = stringResource(id = R.string.gender),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.label_input),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { viewModel.onGenderChange("male") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender == "male",
                            onClick = { viewModel.onGenderChange("male") },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.accent_color),
                                unselectedColor = colorResource(id = R.color.color_border)
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.male),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.input_text),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Row(
                        modifier = Modifier
                            .clickable { viewModel.onGenderChange("female") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender == "female",
                            onClick = { viewModel.onGenderChange("female") },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.accent_color),
                                unselectedColor = colorResource(id = R.color.color_border)
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.female),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.input_text),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                if (genderTouched && genderError.isNotEmpty()) {
                    Text(
                        text = genderError,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.error_color),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.markAllTouched()
                    if (viewModel.validateForm()) {
                        onNavigateToSignUpThree()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.accent_color),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.fromEpochMilliseconds(millis)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date
                        onDateSelected(date)
                    }
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}