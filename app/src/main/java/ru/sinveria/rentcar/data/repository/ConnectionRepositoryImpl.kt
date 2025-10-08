package ru.sinveria.rentcar.data.repository

import kotlinx.coroutines.flow.Flow
import ru.sinveria.rentcar.domain.repository.ConnectionRepository
import ru.sinveria.rentcar.utils.NetworkUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils
) : ConnectionRepository {

    override val isConnected: Flow<Boolean> = networkUtils.isConnected

    override suspend fun checkInstantConnection(): Boolean {
        return networkUtils.isInternetAvailable()
    }
}