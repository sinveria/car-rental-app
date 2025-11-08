package ru.sinveria.rentcar.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.domain.repository.CarRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    private val _cars = MutableStateFlow<List<CarEntity>>(emptyList())
    val cars: StateFlow<List<CarEntity>> = _cars.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var migrationCompleted = false

    init {
        loadCars()
    }

    fun loadCars() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val carsList = carRepository.getAllCars()
                _cars.value = carsList
            } catch (e: Exception) {
                _cars.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun cleanupInvalidCars() {
        try {
            val deletedCount = carRepository.deleteCarsWithContentUris()
            if (deletedCount > 0) {
                loadCars()
            }
        } catch (e: Exception) {
        }
    }

    fun forceCleanup() {
        viewModelScope.launch {
            cleanupInvalidCars()
            loadCars()
        }
    }

    fun reloadCars() {
        viewModelScope.launch {
            loadCars()
        }
    }

    suspend fun migrateExistingCars(context: Context) {
        if (migrationCompleted) {
            return
        }

        try {
            val migratedCount = carRepository.migrateCarPhotosToFileUris(context)

            if (migratedCount > 0) {
                loadCars()
            } else {
                loadCars()
            }

            migrationCompleted = true

        } catch (e: Exception) {
        }
    }

    fun refresh() {
        loadCars()
    }

    fun forceMigration(context: Context) {
        viewModelScope.launch {
            migrationCompleted = false
            migrateExistingCars(context)
        }
    }
}