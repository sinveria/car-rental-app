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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.viewmodel.RegistrationSharedViewModel
import ru.sinveria.rentcar.presentation.viewmodel.SignUpViewModel

@Composable
fun SignUpOne(
    onNavigateBack: () -> Unit = {},
    onNavigateToSignUpTwo: () -> Unit = {},
    viewModel: SignUpViewModel = hiltViewModel(),
    sharedViewModel: RegistrationSharedViewModel
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val emailFocused by viewModel.emailFocused.collectAsState()
    val passwordFocused by viewModel.passwordFocused.collectAsState()
    val confirmPasswordFocused by viewModel.confirmPasswordFocused.collectAsState()
    val agreedToTerms by viewModel.agreedToTerms.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isConfirmPasswordVisible by viewModel.isConfirmPasswordVisible.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val termsError by viewModel.termsError.collectAsState()
    val emailTouched by viewModel.emailTouched.collectAsState()
    val passwordTouched by viewModel.passwordTouched.collectAsState()
    val confirmPasswordTouched by viewModel.confirmPasswordTouched.collectAsState()
    val termsTouched by viewModel.termsTouched.collectAsState()

    LaunchedEffect(Unit) {
        sharedViewModel.clearData()
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
                style = MaterialTheme.typography.titleLarge,
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
                        color = when {
                            emailTouched && emailError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            emailFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = email,
                    onValueChange = viewModel::onEmailChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onEmailFocusChanged(focusState.isFocused)
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
                            if (email.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_email),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (emailTouched && emailError.isNotEmpty()) {
                Text(
                    text = emailError,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.think_password),
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
                        color = when {
                            passwordTouched && passwordError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            passwordFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onPasswordFocusChanged(focusState.isFocused)
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) {
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
                            if (password.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_password),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Image(
                    painter = painterResource(
                        id = if (isPasswordVisible) {
                            R.drawable.visible_pass
                        } else {
                            R.drawable.no_visible_pass
                        }
                    ),
                    contentDescription = if (isPasswordVisible) "Hidden password" else "Show password",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(20.dp)
                        .clickable { viewModel.togglePasswordVisibility() }
                )
            }

            if (passwordTouched && passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.error_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.repeat_password),
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
                        color = when {
                            confirmPasswordTouched && confirmPasswordError.isNotEmpty() -> colorResource(id = R.color.error_color)
                            confirmPasswordFocused -> colorResource(id = R.color.accent_color)
                            else -> colorResource(id = R.color.color_border)
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White)
            ) {
                BasicTextField(
                    value = confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .onFocusChanged { focusState ->
                            viewModel.onConfirmPasswordFocusChanged(focusState.isFocused)
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    singleLine = true,
                    visualTransformation = if (isConfirmPasswordVisible) {
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
                            if (confirmPassword.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.enter_password),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.input_text)
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Image(
                    painter = painterResource(
                        id = if (isConfirmPasswordVisible) {
                            R.drawable.visible_pass
                        } else {
                            R.drawable.no_visible_pass
                        }
                    ),
                    contentDescription = if (isConfirmPasswordVisible) "Hidden password" else "Show password",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(20.dp)
                        .clickable { viewModel.toggleConfirmPasswordVisibility() }
                )
            }

            if (confirmPasswordTouched && confirmPasswordError.isNotEmpty()) {
                Text(
                    text = confirmPasswordError,
                    style = MaterialTheme.typography.bodySmall,
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
                                    termsTouched && termsError.isNotEmpty() -> colorResource(id = R.color.error_color)
                                    else -> colorResource(id = R.color.color_border)
                                },
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Checkbox(
                            checked = agreedToTerms,
                            onCheckedChange = viewModel::onTermsChecked,
                            modifier = Modifier.size(20.dp),
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = Color.Transparent,
                                checkedColor = colorResource(id = R.color.accent_color)
                            )
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.conditions),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.label_input),
                        modifier = Modifier.padding(start = 12.dp),
                        lineHeight = 18.sp
                    )
                }

                if (termsTouched && termsError.isNotEmpty()) {
                    Text(
                        text = termsError,
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
                    viewModel.markAllTouched()
                    if (viewModel.validateForm()) {
                        sharedViewModel.updateStepOneData(
                            email = email,
                            password = password
                        )

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
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}