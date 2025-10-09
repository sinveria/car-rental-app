package ru.sinveria.rentcar.presentation.viewmodel

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.sinveria.rentcar.domain.usecase.ValidateIssueDateUseCase
import ru.sinveria.rentcar.domain.usecase.ValidateLicenseNumberUseCase
import javax.inject.Inject

enum class ImageType {
    PROFILE, LICENSE, PASSPORT
}

@HiltViewModel
class SignUpThreeViewModel @Inject constructor(
    private val validateLicenseNumberUseCase: ValidateLicenseNumberUseCase,
    private val validateIssueDateUseCase: ValidateIssueDateUseCase
) : ViewModel() {

    private val _licenseNumber = MutableStateFlow("")
    val licenseNumber: StateFlow<String> = _licenseNumber.asStateFlow()

    private val _licenseNumberState = MutableStateFlow(TextFieldValue(""))
    val licenseNumberState: StateFlow<TextFieldValue> = _licenseNumberState.asStateFlow()

    private val _issueDate = MutableStateFlow("")
    val issueDate: StateFlow<String> = _issueDate.asStateFlow()

    private val _issueDateState = MutableStateFlow(TextFieldValue(""))
    val issueDateState: StateFlow<TextFieldValue> = _issueDateState.asStateFlow()

    private val _licenseNumberFocused = MutableStateFlow(false)
    val licenseNumberFocused: StateFlow<Boolean> = _licenseNumberFocused.asStateFlow()

    private val _issueDateFocused = MutableStateFlow(false)
    val issueDateFocused: StateFlow<Boolean> = _issueDateFocused.asStateFlow()

    private val _licenseNumberError = MutableStateFlow("")
    val licenseNumberError: StateFlow<String> = _licenseNumberError.asStateFlow()

    private val _issueDateError = MutableStateFlow("")
    val issueDateError: StateFlow<String> = _issueDateError.asStateFlow()

    private val _licenseNumberTouched = MutableStateFlow(false)
    val licenseNumberTouched: StateFlow<Boolean> = _licenseNumberTouched.asStateFlow()

    private val _issueDateTouched = MutableStateFlow(false)
    val issueDateTouched: StateFlow<Boolean> = _issueDateTouched.asStateFlow()

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri.asStateFlow()

    private val _licenseImageUri = MutableStateFlow<Uri?>(null)
    val licenseImageUri: StateFlow<Uri?> = _licenseImageUri.asStateFlow()

    private val _passportImageUri = MutableStateFlow<Uri?>(null)
    val passportImageUri: StateFlow<Uri?> = _passportImageUri.asStateFlow()

    private val _showImageSourceDialog = MutableStateFlow(false)
    val showImageSourceDialog: StateFlow<Boolean> = _showImageSourceDialog.asStateFlow()

    private val _currentImageType = MutableStateFlow<ImageType?>(null)
    val currentImageType: StateFlow<ImageType?> = _currentImageType.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    fun onShowDatePicker() {
        _showDatePicker.value = true
    }

    fun onDismissDatePicker() {
        _showDatePicker.value = false
    }

    fun validateLicenseNumber(): String {
        return validateLicenseNumberUseCase(_licenseNumber.value)
    }

    fun validateIssueDate(): String {
        return validateIssueDateUseCase(_issueDate.value)
    }

    fun validateForm(): Boolean {
        _licenseNumberError.value = validateLicenseNumber()
        _issueDateError.value = validateIssueDate()

        return _licenseNumberError.value.isEmpty() && _issueDateError.value.isEmpty()
    }

    fun onLicenseNumberChange(newValue: TextFieldValue) {
        val cleanInput = newValue.text.filter { it.isDigit() }.take(10)

        _licenseNumberState.value = TextFieldValue(
            text = cleanInput,
            selection = TextRange(cleanInput.length)
        )
        _licenseNumber.value = cleanInput
        _licenseNumberTouched.value = true
        if (_licenseNumberTouched.value) {
            _licenseNumberError.value = validateLicenseNumber()
        }
    }

    fun onIssueDateChange(newValue: TextFieldValue) {
        val cleanInput = newValue.text.filter { it.isDigit() }

        val masked = when {
            cleanInput.length <= 2 -> cleanInput
            cleanInput.length <= 4 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2)}"
            cleanInput.length <= 8 -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4)}"
            else -> "${cleanInput.substring(0, 2)}/${cleanInput.substring(2, 4)}/${cleanInput.substring(4, 8)}"
        }

        val oldLength = _issueDateState.value.text.length
        val newLength = masked.length
        val cursorPos = newValue.selection.start

        var newCursorPos = when {
            newLength > oldLength -> masked.length
            newLength < oldLength -> maxOf(0, cursorPos - 1)
            else -> cursorPos
        }
        newCursorPos = minOf(newCursorPos, masked.length)

        _issueDateState.value = TextFieldValue(
            text = masked,
            selection = TextRange(newCursorPos)
        )

        _issueDate.value = masked
        _issueDateTouched.value = true
        if (_issueDateTouched.value) {
            _issueDateError.value = validateIssueDate()
        }
    }

    fun onLicenseNumberFocusChanged(focused: Boolean) {
        _licenseNumberFocused.value = focused
        if (!focused && _licenseNumberTouched.value) {
            _licenseNumberError.value = validateLicenseNumber()
        }
    }

    fun onIssueDateFocusChanged(focused: Boolean) {
        _issueDateFocused.value = focused
        if (!focused && _issueDateTouched.value) {
            _issueDateError.value = validateIssueDate()
        }
    }

    fun openImageSourceDialog(imageType: ImageType) {
        _currentImageType.value = imageType
        _showImageSourceDialog.value = true
    }

    fun dismissImageSourceDialog() {
        _showImageSourceDialog.value = false
        _currentImageType.value = null
    }

    fun handleCameraSelection() {
        _showImageSourceDialog.value = false
    }

    fun handleGallerySelection() {
        _showImageSourceDialog.value = false
    }

    fun onImageCaptured(uri: Uri?) {
        uri?.let { capturedUri ->
            when (_currentImageType.value) {
                ImageType.PROFILE -> _profileImageUri.value = capturedUri
                ImageType.LICENSE -> _licenseImageUri.value = capturedUri
                ImageType.PASSPORT -> _passportImageUri.value = capturedUri
                null -> {}
            }
        }
        _currentImageType.value = null
    }

    fun onGalleryImageSelected(uri: Uri?) {
        uri?.let {
            when (_currentImageType.value) {
                ImageType.PROFILE -> _profileImageUri.value = it
                ImageType.LICENSE -> _licenseImageUri.value = it
                ImageType.PASSPORT -> _passportImageUri.value = it
                null -> {}
            }
        }
        _currentImageType.value = null
    }

    fun markAllTouched() {
        _licenseNumberTouched.value = true
        _issueDateTouched.value = true

        _licenseNumberError.value = validateLicenseNumber()
        _issueDateError.value = validateIssueDate()
    }

    fun resetForm() {
        _licenseNumber.value = ""
        _licenseNumberState.value = TextFieldValue("")
        _issueDate.value = ""
        _issueDateState.value = TextFieldValue("")
        _licenseNumberError.value = ""
        _issueDateError.value = ""
        _licenseNumberTouched.value = false
        _issueDateTouched.value = false
        _profileImageUri.value = null
        _licenseImageUri.value = null
        _passportImageUri.value = null
    }

    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        _issueDate.value = formattedDate
        _issueDateState.value = TextFieldValue(formattedDate)
        _issueDateTouched.value = true
        _issueDateError.value = validateIssueDate()
        _showDatePicker.value = false
    }
}