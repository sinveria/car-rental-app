package ru.sinveria.rentcar.domain.model

data class RegistrationData(
    val email: String = "",
    val password: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val licenseNumber: String = "",
    val licenseIssueDate: String = "",
    val licensePhotoPath: String? = null,
    val passportPhotoPath: String? = null,
    val profilePhotoPath: String? = null
)