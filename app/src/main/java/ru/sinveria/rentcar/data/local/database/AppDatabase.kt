package ru.sinveria.rentcar.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import ru.sinveria.rentcar.data.local.dao.UserDao
import ru.sinveria.rentcar.data.local.dao.AuthTokenDao
import ru.sinveria.rentcar.data.local.entity.UserEntity
import ru.sinveria.rentcar.data.local.entity.AuthTokenEntity

@Database(
    entities = [UserEntity::class, AuthTokenEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun authTokenDao(): AuthTokenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rentcar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}