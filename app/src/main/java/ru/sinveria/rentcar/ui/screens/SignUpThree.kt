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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview(showBackground = true)
@Composable
fun SignUpThree(
    onNavigateBack: () -> Unit = {},
    onNavigateToCong: () -> Unit = {}
) {
    val licenseNumber = remember { mutableStateOf("") }
    val licenseNumberState = remember { mutableStateOf(TextFieldValue("")) }
    val issueDate = remember { mutableStateOf("") }
    val issueDateState = remember { mutableStateOf(TextFieldValue("")) }

    val licenseNumberFocused = remember { mutableStateOf(false) }
    val issueDateFocused = remember { mutableStateOf(false) }

    val licenseNumberError = remember { mutableStateOf("") }
    val issueDateError = remember { mutableStateOf("") }

    val licenseNumberTouched = remember { mutableStateOf(false) }
    val issueDateTouched = remember { mutableStateOf(false) }
    
    fun isValidLicenseNumber(license: String): Boolean {
        return license.length == 10 && license.all { it.isDigit() }
    }

    fun validateLicenseNumber(license: String): String {
        return when {
            license.isEmpty() -> "Номер водительского удостоверения обязателен"
            !isValidLicenseNumber(license) -> "Номер должен содержать 10 цифр"
            else -> ""
        }
    }

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

    fun validateIssueDate(date: String): String {
        return when {
            date.isEmpty() -> "Дата выдачи обязательна"
            !isValidDateFormat(date) -> "Некорректный формат даты (DD/MM/YYYY)"
            !isValidDate(date) -> "Введите корректную дату выдачи"
            else -> ""
        }
    }

    fun validateForm(): Boolean {
        licenseNumberError.value = validateLicenseNumber(licenseNumber.value)
        issueDateError.value = validateIssueDate(issueDate.value)

        return licenseNumberError.value.isEmpty() && issueDateError.value.isEmpty()
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
                .padding(bottom = 32.dp),
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.color_border),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_photo),
                            contentDescription = "Profile photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = "Add photo",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 3.dp, y = 75.dp)
                    )
                }

                Text(
                    text = stringResource(id = R.string.signupthree_description),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.input_text),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.license_number),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 32.dp)
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
                            licenseNumberTouched.value && licenseNumberError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            licenseNumberFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = licenseNumberState.value,
                    onValueChange = { newValue ->
                        val cleanInput = newValue.text.filter { it.isDigit() }.take(10)

                        licenseNumberState.value = TextFieldValue(
                            text = cleanInput,
                            selection = androidx.compose.ui.text.TextRange(cleanInput.length)
                        )
                        licenseNumber.value = cleanInput
                        licenseNumberTouched.value = true
                        if (licenseNumberTouched.value) {
                            licenseNumberError.value = validateLicenseNumber(cleanInput)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            licenseNumberFocused.value = focusState.isFocused
                            if (!focusState.isFocused && licenseNumberTouched.value) {
                                licenseNumberError.value = validateLicenseNumber(licenseNumber.value)
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
                            if (licenseNumberState.value.text.isEmpty()) {
                                Text(
                                    text = "0000000000",
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (licenseNumberTouched.value && licenseNumberError.value.isNotEmpty()) {
                Text(
                    text = licenseNumberError.value,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.date_of_issue),
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
                            issueDateTouched.value && issueDateError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            issueDateFocused.value -> colorResource(id = R.color.accent_color)
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
                            .padding(start = 16.dp)
                            .size(20.dp)
                    )
                    BasicTextField(
                        value = issueDateState.value,
                        onValueChange = { newValue ->
                            val cleanInput = newValue.text.filter { it.isDigit() }

                            val masked = when {
                                cleanInput.length <= 2 -> cleanInput
                                cleanInput.length <= 4 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2)}"
                                cleanInput.length <= 8 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4)}"
                                else -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4, 8)}"
                            }

                            val oldLength = issueDateState.value.text.length
                            val newLength = masked.length
                            val cursorPos = newValue.selection.start

                            var newCursorPos = when {
                                newLength > oldLength -> masked.length
                                newLength < oldLength -> maxOf(0, cursorPos - 1)
                                else -> cursorPos
                            }
                            newCursorPos = minOf(newCursorPos, masked.length)

                            issueDateState.value = TextFieldValue(
                                text = masked,
                                selection = androidx.compose.ui.text.TextRange(newCursorPos)
                            )

                            issueDate.value = masked
                            issueDateTouched.value = true
                            if (issueDateTouched.value) {
                                issueDateError.value = validateIssueDate(masked)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp, end = 16.dp)
                            .onFocusChanged { focusState ->
                                issueDateFocused.value = focusState.isFocused
                                if (!focusState.isFocused && issueDateTouched.value) {
                                    issueDateError.value = validateIssueDate(issueDate.value)
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
                                if (issueDateState.value.text.isEmpty()) {
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

            if (issueDateTouched.value && issueDateError.value.isNotEmpty()) {
                Text(
                    text = issueDateError.value,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.upload_lisence_photo),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.color_border),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.upload_icon),
                        contentDescription = "Upload license",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.upload_photo),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.label_input),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.upload_passport_photo),
                fontSize = 14.sp,
                color = colorResource(id = R.color.label_input),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.color_border),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.upload_icon),
                        contentDescription = "Upload passport",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.upload_photo),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.label_input),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    licenseNumberTouched.value = true
                    issueDateTouched.value = true

                    if (validateForm()) {
                        onNavigateToCong()
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
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}