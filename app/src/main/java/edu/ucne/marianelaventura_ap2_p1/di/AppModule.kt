package edu.ucne.marianelaventura_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.marianelaventura_ap2_p1.data.local.database.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Huacales.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideEntradaHuacalDao(database: AppDatabase) = database.entradaHuacalDao()
}