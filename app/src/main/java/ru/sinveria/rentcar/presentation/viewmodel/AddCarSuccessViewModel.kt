package ru.sinveria.rentcar.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.data.repository.SharedCarDataRepository
import javax.inject.Inject

@HiltViewModel
class AddCarSuccessViewModel @Inject constructor(
    private val sharedCarDataRepository: SharedCarDataRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Loading)
    val saveState = _saveState.asStateFlow()

    init {
        saveCar()
    }

    fun saveCar() {
        viewModelScope.launch {
            try {
                _saveState.value = SaveState.Loading
                val carId = sharedCarDataRepository.saveCarToDatabase(context)

                if (carId > 0) {
                    _saveState.value = SaveState.Success
                } else {
                    _saveState.value = SaveState.Error("Не удалось сохранить данные об автомобиле")
                }
            } catch (e: Exception) {
                _saveState.value = SaveState.Error("Ошибка: ${e.message}")
            }
        }
    }

    sealed class SaveState {
        object Loading : SaveState()
        object Success : SaveState()
        data class Error(val message: String) : SaveState()
    }
}