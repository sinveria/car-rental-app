package ru.sinveria.rentcar.data.local.dao

import androidx.room.*
import ru.sinveria.rentcar.data.local.entity.AuthTokenEntity

@Dao
interface AuthTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(token: AuthTokenEntity)

    @Query("SELECT * FROM auth_tokens WHERE userId = :userId")
    suspend fun getTokenByUserId(userId: String): AuthTokenEntity?

    @Query("SELECT * FROM auth_tokens WHERE token = :token")
    suspend fun getToken(token: String): AuthTokenEntity?

    @Query("DELETE FROM auth_tokens WHERE userId = :userId")
    suspend fun deleteToken(userId: String)

    @Query("DELETE FROM auth_tokens")
    suspend fun clearAllTokens()
}