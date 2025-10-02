package edu.ucne.marianelaventura_ap2_p1.data.repository

import edu.ucne.marianelaventura_ap2_p1.data.local.dao.EntradaHuacalDao
import edu.ucne.marianelaventura_ap2_p1.data.mappers.toDomain
import edu.ucne.marianelaventura_ap2_p1.data.mappers.toEntity
import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EntradaHuacalRepositoryImpl @Inject constructor(
    private val dao: EntradaHuacalDao
) : EntradaHuacalRepository {

    override suspend fun upsert(entrada: EntradaHuacal) {
        dao.upsert(entrada.toEntity())
    }

    override suspend fun delete(entrada: EntradaHuacal) {
        dao.delete(entrada.toEntity())
    }

    override fun getAll(): Flow<List<EntradaHuacal>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Int): EntradaHuacal? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}