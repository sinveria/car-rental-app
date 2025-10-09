package ru.sinveria.rentcar.presentation.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sinveria.rentcar.domain.usecase.ValidateBirthDateUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateGenderUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateNameUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpTwoViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase
) : ViewModel() {

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _middleName = MutableStateFlow("")
    val middleName: StateFlow<String> = _middleName.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _birthDateState = MutableStateFlow(TextFieldValue(""))
    val birthDateState: StateFlow<TextFieldValue> = _birthDateState.asStateFlow()

    private val _selectedGender = MutableStateFlow("")
    val selectedGender: StateFlow<String> = _selectedGender.asStateFlow()

    private val _lastNameFocused = MutableStateFlow(false)
    val lastNameFocused: StateFlow<Boolean> = _lastNameFocused.asStateFlow()

    private val _firstNameFocused = MutableStateFlow(false)
    val firstNameFocused: StateFlow<Boolean> = _firstNameFocused.asStateFlow()

    private val _middleNameFocused = MutableStateFlow(false)
    val middleNameFocused: StateFlow<Boolean> = _middleNameFocused.asStateFlow()

    private val _birthDateFocused = MutableStateFlow(false)
    val birthDateFocused: StateFlow<Boolean> = _birthDateFocused.asStateFlow()

    private val _lastNameError = MutableStateFlow("")
    val lastNameError: StateFlow<String> = _lastNameError.asStateFlow()

    private val _firstNameError = MutableStateFlow("")
    val firstNameError: StateFlow<String> = _firstNameError.asStateFlow()

    private val _birthDateError = MutableStateFlow("")
    val birthDateError: StateFlow<String> = _birthDateError.asStateFlow()

    private val _genderError = MutableStateFlow("")
    val genderError: StateFlow<String> = _genderError.asStateFlow()

    private val _lastNameTouched = MutableStateFlow(false)
    val lastNameTouched: StateFlow<Boolean> = _lastNameTouched.asStateFlow()

    private val _firstNameTouched = MutableStateFlow(false)
    val firstNameTouched: StateFlow<Boolean> = _firstNameTouched.asStateFlow()

    private val _birthDateTouched = MutableStateFlow(false)
    val birthDateTouched: StateFlow<Boolean> = _birthDateTouched.asStateFlow()

    private val _genderTouched = MutableStateFlow(false)
    val genderTouched: StateFlow<Boolean> = _genderTouched.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    fun onShowDatePicker() {
        _showDatePicker.value = true
    }

    fun onDismissDatePicker() {
        _showDatePicker.value = false
    }

    fun validateLastName(): String {
        return validateNameUseCase(_lastName.value, "Фамилия")
    }

    fun validateFirstName(): String {
        return validateNameUseCase(_firstName.value, "Имя")
    }

    fun validateBirthDate(): String {
        return validateBirthDateUseCase(_birthDate.value)
    }

    fun validateGender(): String {
        return validateGenderUseCase(_selectedGender.value)
    }

    fun validateForm(): Boolean {
        _lastNameError.value = validateLastName()
        _firstNameError.value = validateFirstName()
        _birthDateError.value = validateBirthDate()
        _genderError.value = validateGender()

        return _lastNameError.value.isEmpty() &&
                _firstNameError.value.isEmpty() &&
                _birthDateError.value.isEmpty() &&
                _genderError.value.isEmpty()
    }

    fun onLastNameChange(newLastName: String) {
        _lastName.value = newLastName
        _lastNameTouched.value = true
        if (_lastNameTouched.value) {
            _lastNameError.value = validateLastName()
        }
    }

    fun onFirstNameChange(newFirstName: String) {
        _firstName.value = newFirstName
        _firstNameTouched.value = true
        if (_firstNameTouched.value) {
            _firstNameError.value = validateFirstName()
        }
    }

    fun onMiddleNameChange(newMiddleName: String) {
        _middleName.value = newMiddleName
    }

    fun onBirthDateChange(newValue: TextFieldValue) {
        val cleanInput = newValue.text.filter { it.isDigit() }

        val masked = when {
            cleanInput.length <= 2 -> cleanInput
            cleanInput.length <= 4 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2)}"
            cleanInput.length <= 8 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4)}"
            else -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4, 8)}"
        }

        val oldLength = _birthDateState.value.text.length
        val newLength = masked.length
        val cursorPos = newValue.selection.start

        var newCursorPos = when {
            newLength > oldLength -> masked.length
            newLength < oldLength -> maxOf(0, cursorPos - 1)
            else -> cursorPos
        }
        newCursorPos = minOf(newCursorPos, masked.length)

        _birthDateState.value = TextFieldValue(
            text = masked,
            selection = TextRange(newCursorPos)
        )

        _birthDate.value = masked
        _birthDateTouched.value = true
        if (_birthDateTouched.value) {
            _birthDateError.value = validateBirthDate()
        }
    }

    fun onGenderChange(gender: String) {
        _selectedGender.value = gender
        _genderTouched.value = true
        if (_genderTouched.value) {
            _genderError.value = validateGender()
        }
    }

    fun onLastNameFocusChanged(focused: Boolean) {
        _lastNameFocused.value = focused
        if (!focused && _lastNameTouched.value) {
            _lastNameError.value = validateLastName()
        }
    }

    fun onFirstNameFocusChanged(focused: Boolean) {
        _firstNameFocused.value = focused
        if (!focused && _firstNameTouched.value) {
            _firstNameError.value = validateFirstName()
        }
    }

    fun onBirthDateFocusChanged(focused: Boolean) {
        _birthDateFocused.value = focused
        if (!focused && _birthDateTouched.value) {
            _birthDateError.value = validateBirthDate()
        }
    }

    fun markAllTouched() {
        _lastNameTouched.value = true
        _firstNameTouched.value = true
        _birthDateTouched.value = true
        _genderTouched.value = true

        _lastNameError.value = validateLastName()
        _firstNameError.value = validateFirstName()
        _birthDateError.value = validateBirthDate()
        _genderError.value = validateGender()
    }

    fun resetForm() {
        _lastName.value = ""
        _firstName.value = ""
        _middleName.value = ""
        _birthDate.value = ""
        _birthDateState.value = TextFieldValue("")
        _selectedGender.value = ""
        _lastNameError.value = ""
        _firstNameError.value = ""
        _birthDateError.value = ""
        _genderError.value = ""
        _lastNameTouched.value = false
        _firstNameTouched.value = false
        _birthDateTouched.value = false
        _genderTouched.value = false
    }

    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        _birthDate.value = formattedDate
        _birthDateState.value = TextFieldValue(formattedDate)
        _birthDateTouched.value = true
        _birthDateError.value = validateBirthDate()
        _showDatePicker.value = false
    }
}