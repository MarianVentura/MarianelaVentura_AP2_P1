package edu.ucne.marianelaventura_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "EntradasHuacales")
data class EntradasHuacalesEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntrada: Int = 0,
    val fecha: Date = Date(),
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)