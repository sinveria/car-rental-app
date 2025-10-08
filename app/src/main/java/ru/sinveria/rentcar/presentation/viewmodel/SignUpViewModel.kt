package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sinveria.rentcar.domain.usecase.ValidateConfirmPasswordUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateEmailUseCase
import ru.sinveria.rentcar.domain.usecase.ValidatePasswordUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateTermsUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateTermsUseCase: ValidateTermsUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _emailFocused = MutableStateFlow(false)
    val emailFocused: StateFlow<Boolean> = _emailFocused.asStateFlow()

    private val _passwordFocused = MutableStateFlow(false)
    val passwordFocused: StateFlow<Boolean> = _passwordFocused.asStateFlow()

    private val _confirmPasswordFocused = MutableStateFlow(false)
    val confirmPasswordFocused: StateFlow<Boolean> = _confirmPasswordFocused.asStateFlow()

    private val _agreedToTerms = MutableStateFlow(false)
    val agreedToTerms: StateFlow<Boolean> = _agreedToTerms.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible: StateFlow<Boolean> = _isConfirmPasswordVisible.asStateFlow()

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow("")
    val confirmPasswordError: StateFlow<String> = _confirmPasswordError.asStateFlow()

    private val _termsError = MutableStateFlow("")
    val termsError: StateFlow<String> = _termsError.asStateFlow()

    private val _emailTouched = MutableStateFlow(false)
    val emailTouched: StateFlow<Boolean> = _emailTouched.asStateFlow()

    private val _passwordTouched = MutableStateFlow(false)
    val passwordTouched: StateFlow<Boolean> = _passwordTouched.asStateFlow()

    private val _confirmPasswordTouched = MutableStateFlow(false)
    val confirmPasswordTouched: StateFlow<Boolean> = _confirmPasswordTouched.asStateFlow()

    private val _termsTouched = MutableStateFlow(false)
    val termsTouched: StateFlow<Boolean> = _termsTouched.asStateFlow()

    fun validateEmail(): String {
        return validateEmailUseCase(_email.value)
    }

    fun validatePassword(): String {
        return validatePasswordUseCase(_password.value)
    }

    fun validateConfirmPassword(): String {
        return validateConfirmPasswordUseCase(_password.value, _confirmPassword.value)
    }

    fun validateTerms(): String {
        return validateTermsUseCase(_agreedToTerms.value)
    }

    fun validateForm(): Boolean {
        _emailError.value = validateEmail()
        _passwordError.value = validatePassword()
        _confirmPasswordError.value = validateConfirmPassword()
        _termsError.value = validateTerms()

        return _emailError.value.isEmpty() &&
                _passwordError.value.isEmpty() &&
                _confirmPasswordError.value.isEmpty() &&
                _termsError.value.isEmpty()
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
            if (_confirmPasswordTouched.value) {
                _confirmPasswordError.value = validateConfirmPassword()
            }
        }
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordTouched.value = true
        if (_confirmPasswordTouched.value) {
            _confirmPasswordError.value = validateConfirmPassword()
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

    fun onConfirmPasswordFocusChanged(focused: Boolean) {
        _confirmPasswordFocused.value = focused
        if (!focused && _confirmPasswordTouched.value) {
            _confirmPasswordError.value = validateConfirmPassword()
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    fun onTermsChecked(checked: Boolean) {
        _agreedToTerms.value = checked
        _termsTouched.value = true
        if (_termsTouched.value) {
            _termsError.value = validateTerms()
        }
    }

    fun markAllTouched() {
        _emailTouched.value = true
        _passwordTouched.value = true
        _confirmPasswordTouched.value = true
        _termsTouched.value = true

        _emailError.value = validateEmail()
        _passwordError.value = validatePassword()
        _confirmPasswordError.value = validateConfirmPassword()
        _termsError.value = validateTerms()
    }

    fun resetForm() {
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _agreedToTerms.value = false
        _emailError.value = ""
        _passwordError.value = ""
        _confirmPasswordError.value = ""
        _termsError.value = ""
        _emailTouched.value = false
        _passwordTouched.value = false
        _confirmPasswordTouched.value = false
        _termsTouched.value = false
        _isPasswordVisible.value = false
        _isConfirmPasswordVisible.value = false
    }
}