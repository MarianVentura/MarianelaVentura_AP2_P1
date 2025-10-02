package edu.ucne.marianelaventura_ap2_p1.presentation.entrada

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal

data class EntradaUiState(
    val idEntrada: Int = 0,
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: String = "",
    val precio: String = "",
    val nombreClienteError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null,
    val entradas: List<EntradaHuacal> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isEditing: Boolean = false
)