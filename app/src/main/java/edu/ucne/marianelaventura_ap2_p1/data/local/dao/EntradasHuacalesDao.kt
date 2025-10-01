package edu.ucne.marianelaventura_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradasHuacalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradasHuacalesDao {
    @Upsert
    suspend fun upsert(entrada: EntradasHuacalesEntity)

    @Query("SELECT * FROM EntradasHuacales WHERE idEntrada = :id")
    suspend fun getById(id: Int): EntradasHuacalesEntity?

    @Delete
    suspend fun delete(entrada: EntradasHuacalesEntity)

    @Query("SELECT * FROM EntradasHuacales ORDER BY fecha DESC")
    fun getAll(): Flow<List<EntradasHuacalesEntity>>


    @Query("""
        SELECT * FROM EntradasHuacales 
        WHERE (:nombreCliente IS NULL OR nombreCliente LIKE '%' || :nombreCliente || '%')
        AND (:fechaInicio IS NULL OR fecha >= :fechaInicio)
        AND (:fechaFin IS NULL OR fecha <= :fechaFin)
        AND (:cantidadMin IS NULL OR cantidad >= :cantidadMin)
        AND (:cantidadMax IS NULL OR cantidad <= :cantidadMax)
        AND (:precioMin IS NULL OR precio >= :precioMin)
        AND (:precioMax IS NULL OR precio <= :precioMax)
        ORDER BY fecha DESC
    """)
    fun getFiltered(
        nombreCliente: String? = null,
        fechaInicio: Long? = null,
        fechaFin: Long? = null,
        cantidadMin: Int? = null,
        cantidadMax: Int? = null,
        precioMin: Double? = null,
        precioMax: Double? = null
    ): Flow<List<EntradasHuacalesEntity>>
}