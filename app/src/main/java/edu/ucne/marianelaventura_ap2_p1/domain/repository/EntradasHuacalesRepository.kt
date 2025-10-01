package edu.ucne.marianelaventura_ap2_p1.domain.repository

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradasHuacales
import kotlinx.coroutines.flow.Flow

interface EntradasHuacalesRepository {
    suspend fun upsert(entrada: EntradasHuacales)
    suspend fun getById(id: Int): EntradasHuacales?
    suspend fun delete(entrada: EntradasHuacales)
    fun getAll(): Flow<List<EntradasHuacales>>
    fun getFiltered(
        nombreCliente: String? = null,
        fechaInicio: Long? = null,
        fechaFin: Long? = null,
        cantidadMin: Int? = null,
        cantidadMax: Int? = null,
        precioMin: Double? = null,
        precioMax: Double? = null
    ): Flow<List<EntradasHuacales>>
}