package edu.ucne.marianelaventura_ap2_p1.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.marianelaventura_ap2_p1.data.repository.EntradaHuacalRepositoryImpl
import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEntradaHuacalRepository(
        entradaHuacalRepositoryImpl: EntradaHuacalRepositoryImpl
    ): EntradaHuacalRepository
}