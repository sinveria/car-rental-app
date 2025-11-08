package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.data.repository.LocalRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val localRepository: LocalRepositoryImpl
) : ViewModel() {

    private val _userData = MutableStateFlow<UserEntity?>(null)
    val userData: StateFlow<UserEntity?> = _userData.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadUserData(email: String) {
        viewModelScope.launch {
            try {
                val user = localRepository.getUserByEmail(email)
                _userData.value = user
                println("Loaded user data for profile: ${user?.email}")
            } catch (e: Exception) {
                println("Error loading user data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadLastRegisteredUser() {
        viewModelScope.launch {
            try {
                val allUsers = localRepository.getAllUsers()
                val lastUser = allUsers.lastOrNull()
                _userData.value = lastUser
                println("Loaded last user for profile: ${lastUser?.email}")
            } catch (e: Exception) {
                println("Error loading last user: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}