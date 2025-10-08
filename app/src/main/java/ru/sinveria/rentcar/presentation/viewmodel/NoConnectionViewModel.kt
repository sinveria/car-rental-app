package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.domain.usecase.CheckConnectionUseCase
import javax.inject.Inject

@HiltViewModel
class NoConnectionViewModel @Inject constructor(
    private val checkConnectionUseCase: CheckConnectionUseCase
) : ViewModel() {

    val isConnected = checkConnectionUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    fun checkConnection() {
        viewModelScope.launch {
            checkConnectionUseCase.checkInstantConnection()
        }
    }
}