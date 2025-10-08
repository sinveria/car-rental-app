package ru.sinveria.rentcar.domain.usecase

import android.content.Context
import android.net.Uri
import ru.sinveria.rentcar.utils.createImageUri
import javax.inject.Inject

class ImagePickerUseCase @Inject constructor() {
    fun createImageUri(context: Context): Uri {
        return createImageUri(context)
    }
}