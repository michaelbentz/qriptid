package com.michaelbentz.qriptid.di

import android.content.Context
import com.michaelbentz.qriptid.database.Database
import com.michaelbentz.qriptid.database.dao.QrCodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Database.getInstance(context)
    }

    @Provides
    fun provideQrCodeDao(database: Database): QrCodeDao {
        return database.qrCodeDao()
    }
}
