package ru.sinveria.rentcar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sinveria.rentcar.data.local.database.AppDatabase
import ru.sinveria.rentcar.data.local.dao.UserDao
import ru.sinveria.rentcar.data.local.dao.AuthTokenDao
import ru.sinveria.rentcar.data.local.dao.CarDao
import ru.sinveria.rentcar.data.repository.CarRepositoryImpl
import ru.sinveria.rentcar.data.repository.LocalRepositoryImpl
import ru.sinveria.rentcar.domain.repository.CarRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideAuthTokenDao(database: AppDatabase): AuthTokenDao {
        return database.authTokenDao()
    }

    @Provides
    @Singleton
    fun provideCarDao(database: AppDatabase): CarDao {
        return database.carDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(
        userDao: UserDao,
        authTokenDao: AuthTokenDao
    ): LocalRepositoryImpl {
        return LocalRepositoryImpl(userDao, authTokenDao)
    }

    @Provides
    @Singleton
    fun provideCarRepository(carDao: CarDao): CarRepository {
        return CarRepositoryImpl(carDao)
    }
}