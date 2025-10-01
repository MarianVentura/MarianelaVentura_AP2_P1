package edu.ucne.marianelaventura_ap2_p1.domain.model

import java.util.Date

data class EntradasHuacales(
    val idEntrada: Int = 0,
    val fecha: Date = Date(),
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)