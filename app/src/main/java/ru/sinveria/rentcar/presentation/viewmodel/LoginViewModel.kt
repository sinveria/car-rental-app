package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.domain.usecase.LoginUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateEmailUseCase
import ru.sinveria.rentcar.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _emailFocused = MutableStateFlow(false)
    val emailFocused: StateFlow<Boolean> = _emailFocused.asStateFlow()

    private val _passwordFocused = MutableStateFlow(false)
    val passwordFocused: StateFlow<Boolean> = _passwordFocused.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError.asStateFlow()

    private val _emailTouched = MutableStateFlow(false)
    val emailTouched: StateFlow<Boolean> = _emailTouched.asStateFlow()

    private val _passwordTouched = MutableStateFlow(false)
    val passwordTouched: StateFlow<Boolean> = _passwordTouched.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun validateEmail(): String {
        return validateEmailUseCase(_email.value)
    }

    fun validatePassword(): String {
        return validatePasswordUseCase(_password.value)
    }

    fun validateForm(): Boolean {
        _emailError.value = validateEmail()
        _passwordError.value = validatePassword()
        return _emailError.value.isEmpty() && _passwordError.value.isEmpty()
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _emailTouched.value = true
        if (_emailTouched.value) {
            _emailError.value = validateEmail()
        }
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordTouched.value = true
        if (_passwordTouched.value) {
            _passwordError.value = validatePassword()
        }
    }

    fun onEmailFocusChanged(focused: Boolean) {
        _emailFocused.value = focused
        if (!focused && _emailTouched.value) {
            _emailError.value = validateEmail()
        }
    }

    fun onPasswordFocusChanged(focused: Boolean) {
        _passwordFocused.value = focused
        if (!focused && _passwordTouched.value) {
            _passwordError.value = validatePassword()
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!validateForm()) return

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val result = loginUseCase(_email.value, _password.value)
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    onError(result.errorMessage ?: "Ошибка входа")
                }
            } catch (e: Exception) {
                onError("Ошибка сети: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetForm() {
        _email.value = ""
        _password.value = ""
        _emailError.value = ""
        _passwordError.value = ""
        _emailTouched.value = false
        _passwordTouched.value = false
        _isPasswordVisible.value = false
    }
}