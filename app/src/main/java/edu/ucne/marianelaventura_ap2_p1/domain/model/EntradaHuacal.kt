package edu.ucne.marianelaventura_ap2_p1.domain.model

data class EntradaHuacal(
    val idEntrada: Int = 0,
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)