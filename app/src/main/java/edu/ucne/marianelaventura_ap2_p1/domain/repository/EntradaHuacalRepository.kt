package edu.ucne.marianelaventura_ap2_p1.domain.repository

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import kotlinx.coroutines.flow.Flow

interface EntradaHuacalRepository {
    suspend fun upsert(entrada: EntradaHuacal)
    suspend fun delete(entrada: EntradaHuacal)
    fun getAll(): Flow<List<EntradaHuacal>>
    suspend fun getById(id: Int): EntradaHuacal?
    suspend fun deleteById(id: Int)
}