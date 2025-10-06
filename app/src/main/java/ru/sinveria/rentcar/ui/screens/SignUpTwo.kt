package ru.sinveria.rentcar.ui.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview(showBackground = true)
@Composable
fun SignUpTwo(
    onNavigateBack: () -> Unit = {},
    onNavigateToSignUpThree: () -> Unit = {}
) {
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val middleName = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val birthDateState = remember { mutableStateOf(TextFieldValue("")) }

    val lastNameFocused = remember { mutableStateOf(false) }
    val firstNameFocused = remember { mutableStateOf(false) }
    val middleNameFocused = remember { mutableStateOf(false) }
    val birthDateFocused = remember { mutableStateOf(false) }
    val selectedGender = remember { mutableStateOf("") }

    val lastNameError = remember { mutableStateOf("") }
    val firstNameError = remember { mutableStateOf("") }
    val birthDateError = remember { mutableStateOf("") }
    val genderError = remember { mutableStateOf("") }

    val lastNameTouched = remember { mutableStateOf(false) }
    val firstNameTouched = remember { mutableStateOf(false) }
    val birthDateTouched = remember { mutableStateOf(false) }
    val genderTouched = remember { mutableStateOf(false) }

    fun isValidDateFormat(date: String): Boolean {
        val pattern = "^\\d{2}/\\d{2}/\\d{4}$".toRegex()
        return pattern.matches(date)
    }

    fun isValidDate(date: String): Boolean {
        if (!isValidDateFormat(date)) return false

        val parts = date.split("/")
        if (parts.size != 3) return false

        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false

        if (year < 1900 || year > 2100) return false
        if (month < 1 || month > 12) return false

        val daysInMonth = when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        return day in 1..daysInMonth
    }

    fun validateLastName(lastName: String): String {
        return if (lastName.isEmpty()) "Фамилия обязательна" else ""
    }

    fun validateFirstName(firstName: String): String {
        return if (firstName.isEmpty()) "Имя обязательно" else ""
    }

    fun validateBirthDate(birthDate: String): String {
        return when {
            birthDate.isEmpty() -> "Дата рождения обязательна"
            !isValidDateFormat(birthDate) -> "Некорректный формат даты (DD/MM/YYYY)"
            !isValidDate(birthDate) -> "Введите корректную дату рождения"
            else -> ""
        }
    }

    fun validateGender(gender: String): String {
        return if (gender.isEmpty()) "Выбор пола обязателен" else ""
    }

    fun validateForm(): Boolean {
        lastNameError.value = validateLastName(lastName.value)
        firstNameError.value = validateFirstName(firstName.value)
        birthDateError.value = validateBirthDate(birthDate.value)
        genderError.value = validateGender(selectedGender.value)

        return lastNameError.value.isEmpty() &&
                firstNameError.value.isEmpty() &&
                birthDateError.value.isEmpty() &&
                genderError.value.isEmpty()
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
                            lastNameTouched.value && lastNameError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            lastNameFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = lastName.value,
                    onValueChange = {
                        lastName.value = it
                        lastNameTouched.value = true
                        if (lastNameTouched.value) {
                            lastNameError.value = validateLastName(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            lastNameFocused.value = focusState.isFocused
                            if (!focusState.isFocused && lastNameTouched.value) {
                                lastNameError.value = validateLastName(lastName.value)
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
                            if (lastName.value.isEmpty()) {
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

            if (lastNameTouched.value && lastNameError.value.isNotEmpty()) {
                Text(
                    text = lastNameError.value,
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
                            firstNameTouched.value && firstNameError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            firstNameFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = firstName.value,
                    onValueChange = {
                        firstName.value = it
                        firstNameTouched.value = true
                        if (firstNameTouched.value) {
                            firstNameError.value = validateFirstName(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            firstNameFocused.value = focusState.isFocused
                            if (!focusState.isFocused && firstNameTouched.value) {
                                firstNameError.value = validateFirstName(firstName.value)
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
                            if (firstName.value.isEmpty()) {
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

            if (firstNameTouched.value && firstNameError.value.isNotEmpty()) {
                Text(
                    text = firstNameError.value,
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
                        color = if (middleNameFocused.value) colorResource(id = R.color.accent_color)
                        else colorResource(id = R.color.color_border),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = middleName.value,
                    onValueChange = { middleName.value = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            middleNameFocused.value = focusState.isFocused
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
                            if (middleName.value.isEmpty()) {
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
                            birthDateTouched.value && birthDateError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            birthDateFocused.value -> colorResource(id = R.color.accent_color)
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
                    )

                    BasicTextField(
                        value = birthDateState.value,
                        onValueChange = { newValue ->
                            val cleanInput = newValue.text.filter { it.isDigit() }

                            val masked = when {
                                cleanInput.length <= 2 -> cleanInput
                                cleanInput.length <= 4 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2)}"
                                cleanInput.length <= 8 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4)}"
                                else -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4, 8)}"
                            }

                            // Простая эвристика позиции курсора
                            val oldLength = birthDateState.value.text.length
                            val newLength = masked.length
                            val cursorPos = newValue.selection.start

                            var newCursorPos = when {
                                newLength > oldLength -> masked.length // ввод — курсор в конец
                                newLength < oldLength -> maxOf(0, cursorPos - 1) // удаление — сдвигаем назад
                                else -> cursorPos
                            }
                            newCursorPos = minOf(newCursorPos, masked.length)

                            birthDateState.value = TextFieldValue(
                                text = masked,
                                selection = androidx.compose.ui.text.TextRange(newCursorPos)
                            )

                            birthDate.value = masked
                            birthDateTouched.value = true
                            if (birthDateTouched.value) {
                                birthDateError.value = validateBirthDate(masked)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { focusState ->
                                birthDateFocused.value = focusState.isFocused
                                if (!focusState.isFocused && birthDateTouched.value) {
                                    birthDateError.value = validateBirthDate(birthDate.value)
                                }
                            },
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
                                if (birthDateState.value.text.isEmpty()) {
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

            if (birthDateTouched.value && birthDateError.value.isNotEmpty()) {
                Text(
                    text = birthDateError.value,
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
                            .clickable {
                                selectedGender.value = "male"
                                genderTouched.value = true
                                if (genderTouched.value) {
                                    genderError.value = validateGender("male")
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender.value == "male",
                            onClick = {
                                selectedGender.value = "male"
                                genderTouched.value = true
                                if (genderTouched.value) {
                                    genderError.value = validateGender("male")
                                }
                            },
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
                            .clickable {
                                selectedGender.value = "female"
                                genderTouched.value = true
                                if (genderTouched.value) {
                                    genderError.value = validateGender("female")
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender.value == "female",
                            onClick = {
                                selectedGender.value = "female"
                                genderTouched.value = true
                                if (genderTouched.value) {
                                    genderError.value = validateGender("female")
                                }
                            },
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

                if (genderTouched.value && genderError.value.isNotEmpty()) {
                    Text(
                        text = genderError.value,
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
                    lastNameTouched.value = true
                    firstNameTouched.value = true
                    birthDateTouched.value = true
                    genderTouched.value = true

                    if (validateForm()) {
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