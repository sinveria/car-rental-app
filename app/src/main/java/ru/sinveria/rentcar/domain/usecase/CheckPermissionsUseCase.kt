package ru.sinveria.rentcar.domain.usecase

import android.Manifest
import android.content.Context
import ru.sinveria.rentcar.utils.getGalleryPermission
import ru.sinveria.rentcar.utils.hasCameraPermission
import ru.sinveria.rentcar.utils.hasGalleryPermission
import javax.inject.Inject

class CheckPermissionsUseCase @Inject constructor() {
    fun hasCameraPermission(context: Context): Boolean {
        return hasCameraPermission(context)
    }

    fun hasGalleryPermission(context: Context): Boolean {
        return hasGalleryPermission(context)
    }

    fun getGalleryPermission(): String {
        return getGalleryPermission()
    }
}