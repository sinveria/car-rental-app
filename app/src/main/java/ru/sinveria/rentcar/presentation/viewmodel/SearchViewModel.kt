package ru.sinveria.rentcar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sinveria.rentcar.data.local.entity.CarEntity
import ru.sinveria.rentcar.domain.repository.CarRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<CarEntity>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun searchCars(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val allCars = carRepository.getAllCars()
                val normalizedQuery = query.trim().lowercase()

                val results = allCars.filter { car ->
                    val searchText = "${car.brand} ${car.model}".lowercase()
                    searchText.contains(normalizedQuery)
                }

                _searchResults.value = results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}