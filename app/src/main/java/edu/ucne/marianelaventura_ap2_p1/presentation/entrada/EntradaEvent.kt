package edu.ucne.marianelaventura_ap2_p1.presentation.entrada

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal

sealed class EntradaEvent {
    data class NombreClienteChanged(val nombreCliente: String) : EntradaEvent()
    data class CantidadChanged(val cantidad: String) : EntradaEvent()
    data class PrecioChanged(val precio: String) : EntradaEvent()
    data object SaveEntrada : EntradaEvent()
    data object ClearForm : EntradaEvent()
    data class DeleteEntrada(val idEntrada: Int) : EntradaEvent()
    data class SelectEntrada(val entrada: EntradaHuacal) : EntradaEvent()
}