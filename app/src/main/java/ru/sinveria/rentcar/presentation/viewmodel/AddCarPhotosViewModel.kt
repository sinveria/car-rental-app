package ru.sinveria.rentcar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.net.Uri
import android.content.Context
import java.io.File
import java.io.InputStream
import ru.sinveria.rentcar.data.repository.SharedCarDataRepository
import javax.inject.Inject

@HiltViewModel
class AddCarPhotosViewModel @Inject constructor(
    private val sharedCarDataRepository: SharedCarDataRepository
) : ViewModel() {

    private val _showImageSourceDialog = MutableStateFlow(false)
    val showImageSourceDialog = _showImageSourceDialog.asStateFlow()

    private val _currentImageType = MutableStateFlow<CarImageType?>(null)
    val currentImageType = _currentImageType.asStateFlow()

    private val _mainPhotoUri = MutableStateFlow<Uri?>(null)
    val mainPhotoUri = _mainPhotoUri.asStateFlow()

    private val _photo1Uri = MutableStateFlow<Uri?>(null)
    val photo1Uri = _photo1Uri.asStateFlow()

    private val _photo2Uri = MutableStateFlow<Uri?>(null)
    val photo2Uri = _photo2Uri.asStateFlow()

    private val _photo3Uri = MutableStateFlow<Uri?>(null)
    val photo3Uri = _photo3Uri.asStateFlow()

    private val _photo4Uri = MutableStateFlow<Uri?>(null)
    val photo4Uri = _photo4Uri.asStateFlow()

    private val _isNextEnabled = MutableStateFlow(false)
    val isNextEnabled = _isNextEnabled.asStateFlow()

    private val permanentUris = mutableListOf<String>()

    private fun checkNextEnabled() {
        val allPhotosUploaded = mainPhotoUri.value != null &&
                photo1Uri.value != null &&
                photo2Uri.value != null &&
                photo3Uri.value != null &&
                photo4Uri.value != null

        _isNextEnabled.value = allPhotosUploaded
    }

    fun openImageSourceDialog(CarImageType: CarImageType) {
        _currentImageType.value = CarImageType
        _showImageSourceDialog.value = true
    }

    fun dismissImageSourceDialog() {
        _showImageSourceDialog.value = false
        _currentImageType.value = null
    }

    fun onImageCaptured(uri: Uri?, context: Context) {
        uri?.let { safeUri ->
            viewModelScope.launch {
                val permanentUri = copyToAppStorage(safeUri, context)
                permanentUri?.let { permanent ->
                    when (_currentImageType.value) {
                        CarImageType.CAR_MAIN -> _mainPhotoUri.value = permanent
                        CarImageType.CAR_SIDE1 -> _photo1Uri.value = permanent
                        CarImageType.CAR_SIDE2 -> _photo2Uri.value = permanent
                        CarImageType.CAR_INTERIOR -> _photo3Uri.value = permanent
                        CarImageType.CAR_REAR -> _photo4Uri.value = permanent
                        null -> return@launch
                    }
                    permanentUris.add(permanent.toString())
                    updateSharedViewModel()
                    checkNextEnabled()
                }
            }
        }
        dismissImageSourceDialog()
    }

    fun onGalleryImageSelected(uri: Uri?, context: Context) {
        uri?.let { safeUri ->
            viewModelScope.launch {
                val permanentUri = copyToAppStorage(safeUri, context)
                permanentUri?.let { permanent ->
                    when (_currentImageType.value) {
                        CarImageType.CAR_MAIN -> _mainPhotoUri.value = permanent
                        CarImageType.CAR_SIDE1 -> _photo1Uri.value = permanent
                        CarImageType.CAR_SIDE2 -> _photo2Uri.value = permanent
                        CarImageType.CAR_INTERIOR -> _photo3Uri.value = permanent
                        CarImageType.CAR_REAR -> _photo4Uri.value = permanent
                        null -> return@launch
                    }
                    permanentUris.add(permanent.toString())
                    updateSharedViewModel()
                    checkNextEnabled()
                }
            }
        }
        dismissImageSourceDialog()
    }

    private suspend fun copyToAppStorage(uri: Uri, context: Context): Uri? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val fileName = "car_photo_${System.currentTimeMillis()}.jpg"
            val outputFile = File(context.filesDir, fileName)

            inputStream?.use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            Uri.fromFile(outputFile)
        } catch (e: Exception) {
            null
        }
    }

    private fun updateSharedViewModel() {
        val photoUris = permanentUris.joinToString(",")
        sharedCarDataRepository.updatePhotos(photoUris)
    }

    fun handleCameraSelection() {
    }

    fun handleGallerySelection() {
    }

    fun getPhotoCount(): Int {
        return listOfNotNull(
            mainPhotoUri.value,
            photo1Uri.value,
            photo2Uri.value,
            photo3Uri.value,
            photo4Uri.value
        ).size
    }

    fun getAllPhotoUris(): List<Uri> {
        return listOfNotNull(
            mainPhotoUri.value,
            photo1Uri.value,
            photo2Uri.value,
            photo3Uri.value,
            photo4Uri.value
        )
    }

    fun clearPhotos() {
        _mainPhotoUri.value = null
        _photo1Uri.value = null
        _photo2Uri.value = null
        _photo3Uri.value = null
        _photo4Uri.value = null
        permanentUris.clear()
    }
}