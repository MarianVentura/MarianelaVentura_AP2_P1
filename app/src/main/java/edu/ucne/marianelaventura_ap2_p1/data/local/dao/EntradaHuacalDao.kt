package edu.ucne.marianelaventura_ap2_p1.data.local.dao

import androidx.room.*
import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradaHuacalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradaHuacalDao {
    @Upsert
    suspend fun upsert(entrada: EntradaHuacalEntity)

    @Delete
    suspend fun delete(entrada: EntradaHuacalEntity)

    @Query("SELECT * FROM EntradasHuacales ORDER BY idEntrada DESC")
    fun getAll(): Flow<List<EntradaHuacalEntity>>

    @Query("SELECT * FROM EntradasHuacales WHERE idEntrada = :id")
    suspend fun getById(id: Int): EntradaHuacalEntity?

    @Query("DELETE FROM EntradasHuacales WHERE idEntrada = :id")
    suspend fun deleteById(id: Int)
}