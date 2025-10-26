package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.domain.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    fun registerUser(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String,
        gender: String,
        licenseNumber: String,
        licenseIssueDate: String,
        licensePhotoPath: String?,
        passportPhotoPath: String?,
        profilePhotoPath: String?
    ) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                val result = registerUseCase(
                    email = email,
                    password = password,
                    lastName = lastName,
                    firstName = firstName,
                    middleName = middleName,
                    birthDate = birthDate,
                    gender = gender,
                    licenseNumber = licenseNumber,
                    licenseIssueDate = licenseIssueDate,
                    licensePhotoPath = licensePhotoPath,
                    passportPhotoPath = passportPhotoPath,
                    profilePhotoPath = profilePhotoPath
                )
                if (result.isSuccess) {
                    _registrationState.value = RegistrationState.Success(result.token)
                } else {
                    _registrationState.value = RegistrationState.Error(
                        result.errorMessage ?: "Ошибка регистрации"
                    )
                }
            } catch (e: Exception) {
                _registrationState.value = RegistrationState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}