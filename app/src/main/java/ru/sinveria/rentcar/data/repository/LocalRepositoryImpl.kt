package ru.sinveria.rentcar.data.repository

import ru.sinveria.rentcar.data.local.dao.UserDao
import ru.sinveria.rentcar.data.local.dao.AuthTokenDao
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.data.local.entity.AuthTokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authTokenDao: AuthTokenDao
) {

    suspend fun saveUser(user: UserEntity) = userDao.insertUser(user)

    suspend fun getUserByEmail(email: String): UserEntity? =
        userDao.getUserByEmail(email)

    suspend fun getUserById(userId: String): UserEntity? =
        userDao.getUserById(userId)

    suspend fun getAllUsers(): List<UserEntity> =
        userDao.getAllUsers()

    suspend fun saveToken(token: AuthTokenEntity) =
        authTokenDao.insertToken(token)

    suspend fun getTokenByUserId(userId: String): AuthTokenEntity? =
        authTokenDao.getTokenByUserId(userId)

    suspend fun getCurrentToken(): AuthTokenEntity? {
        return null
    }

    suspend fun clearTokens() = authTokenDao.clearAllTokens()

    suspend fun getCurrentUserId(userId: String): String? {
        val tokenEntity = authTokenDao.getTokenByUserId(userId)
        return tokenEntity?.userId ?: tokenEntity?.userId
    }
}