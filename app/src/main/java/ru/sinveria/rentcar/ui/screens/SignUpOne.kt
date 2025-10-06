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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview(showBackground = true)
@Composable
fun SignUpOne(
    onNavigateBack: () -> Unit = {},
    onNavigateToSignUpTwo: () -> Unit = {}
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val emailFocused = remember { mutableStateOf(false) }
    val passwordFocused = remember { mutableStateOf(false) }
    val confirmPasswordFocused = remember { mutableStateOf(false) }
    val agreedToTerms = remember { mutableStateOf(false) }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val isConfirmPasswordVisible = remember { mutableStateOf(false) }

    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf("") }
    val termsError = remember { mutableStateOf("") }

    val emailTouched = remember { mutableStateOf(false) }
    val passwordTouched = remember { mutableStateOf(false) }
    val confirmPasswordTouched = remember { mutableStateOf(false) }
    val termsTouched = remember { mutableStateOf(false) }

    fun validateEmail(email: String): String {

        return when {
            email.isEmpty() -> "Электронная почта обязательна"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> "Введите корректный адрес электронной почты"
            else -> ""
        }
    }

    fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> "Пароль обязателен"
            else -> ""
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String {
        return when {
            confirmPassword.isEmpty() -> "Подтверждение пароля обязательно"
            password != confirmPassword -> "Пароли не совпадают"
            else -> ""
        }
    }

    fun validateTerms(agreed: Boolean): String {
        return if (!agreed) "Необходимо согласиться с условиями обслуживания \n" +
                "и политикой конфиденциальности" else ""
    }

    fun validateForm(): Boolean {
        emailError.value = validateEmail(email.value)
        passwordError.value = validatePassword(password.value)
        confirmPasswordError.value = validateConfirmPassword(password.value, confirmPassword.value)
        termsError.value = validateTerms(agreedToTerms.value)

        return emailError.value.isEmpty() &&
                passwordError.value.isEmpty() &&
                confirmPasswordError.value.isEmpty() &&
                termsError.value.isEmpty()
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
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.email),
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
                            emailTouched.value && emailError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            emailFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailTouched.value = true
                        if (emailTouched.value) {
                            emailError.value = validateEmail(it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            emailFocused.value = focusState.isFocused
                            if (!focusState.isFocused && emailTouched.value) {
                                emailError.value = validateEmail(email.value)
                            }
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (email.value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_email),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (emailTouched.value && emailError.value.isNotEmpty()) {
                Text(
                    text = emailError.value,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.think_password),
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
                            passwordTouched.value && passwordError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            passwordFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordTouched.value = true
                        if (passwordTouched.value) {
                            passwordError.value = validatePassword(it)
                            if (confirmPasswordTouched.value) {
                                confirmPasswordError.value = validateConfirmPassword(it, confirmPassword.value)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            passwordFocused.value = focusState.isFocused
                            if (!focusState.isFocused && passwordTouched.value) {
                                passwordError.value = validatePassword(password.value)
                            }
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (password.value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_password),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Image(
                    painter = painterResource(
                        id = if (isPasswordVisible.value) {
                            R.drawable.visible_pass
                        } else {
                            R.drawable.no_visible_pass
                        }
                    ),
                    contentDescription = if (isPasswordVisible.value) {
                        "Скрыть пароль"
                    } else {
                        "Показать пароль"
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(20.dp)
                        .clickable {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }
                )
            }

            if (passwordTouched.value && passwordError.value.isNotEmpty()) {
                Text(
                    text = passwordError.value,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.repeat_password),
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
                            confirmPasswordTouched.value && confirmPasswordError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                            confirmPasswordFocused.value -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = confirmPassword.value,
                    onValueChange = {
                        confirmPassword.value = it
                        confirmPasswordTouched.value = true
                        if (confirmPasswordTouched.value) {
                            confirmPasswordError.value = validateConfirmPassword(password.value, it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            confirmPasswordFocused.value = focusState.isFocused
                            if (!focusState.isFocused && confirmPasswordTouched.value) {
                                confirmPasswordError.value = validateConfirmPassword(password.value, confirmPassword.value)
                            }
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    visualTransformation = if (isConfirmPasswordVisible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (confirmPassword.value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_password),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Image(
                    painter = painterResource(
                        id = if (isConfirmPasswordVisible.value) {
                            R.drawable.visible_pass
                        } else {
                            R.drawable.no_visible_pass
                        }
                    ),
                    contentDescription = if (isConfirmPasswordVisible.value) {
                        "Скрыть пароль"
                    } else {
                        "Показать пароль"
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(20.dp)
                        .clickable {
                            isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value
                        }
                )
            }

            if (confirmPasswordTouched.value && confirmPasswordError.value.isNotEmpty()) {
                Text(
                    text = confirmPasswordError.value,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                width = 1.dp,
                                color = when {
                                    termsTouched.value && termsError.value.isNotEmpty() -> colorResource(id = R.color.error_color)
                                    else -> colorResource(id = R.color.color_border)
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Checkbox(
                            checked = agreedToTerms.value,
                            onCheckedChange = {
                                agreedToTerms.value = it
                                termsTouched.value = true
                                if (termsTouched.value) {
                                    termsError.value = validateTerms(it)
                                }
                            },
                            modifier = Modifier.size(20.dp),
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = Color.Transparent,
                                checkedColor = colorResource(id = R.color.accent_color)
                            )
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.conditions),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.label_input),
                        modifier = Modifier.padding(start = 12.dp),
                        lineHeight = 18.sp
                    )
                }

                if (termsTouched.value && termsError.value.isNotEmpty()) {
                    Text(
                        text = termsError.value,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.error_color),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    emailTouched.value = true
                    passwordTouched.value = true
                    confirmPasswordTouched.value = true
                    termsTouched.value = true

                    if (validateForm()) {
                        onNavigateToSignUpTwo()
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