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
import androidx.compose.material3.Text
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview(showBackground = true)
@Composable
fun SignUpTwo() {
    val lastName = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val middleName = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val lastNameFocused = remember { mutableStateOf(false) }
    val firstNameFocused = remember { mutableStateOf(false) }
    val middleNameFocused = remember { mutableStateOf(false) }
    val birthDateFocused = remember { mutableStateOf(false) }
    val selectedGender = remember { mutableStateOf("") }

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
                    .clickable { }
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
                        color = if (lastNameFocused.value) colorResource(id = R.color.accent_color)
                        else colorResource(id = R.color.color_border),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            lastNameFocused.value = focusState.isFocused
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
                        color = if (firstNameFocused.value) colorResource(id = R.color.accent_color)
                        else colorResource(id = R.color.color_border),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            firstNameFocused.value = focusState.isFocused
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
                        color = if (birthDateFocused.value) colorResource(id = R.color.accent_color)
                        else colorResource(id = R.color.color_border),
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
                        value = birthDate.value,
                        onValueChange = { birthDate.value = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp, end = 16.dp)
                            .onFocusChanged { focusState ->
                                birthDateFocused.value = focusState.isFocused
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
                                if (birthDate.value.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.enter_birthdate),
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
                        .clickable { selectedGender.value = "male" },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender.value == "male",
                        onClick = { selectedGender.value = "male" },
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
                        .clickable { selectedGender.value = "female" },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender.value == "female",
                        onClick = { selectedGender.value = "female" },
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

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
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