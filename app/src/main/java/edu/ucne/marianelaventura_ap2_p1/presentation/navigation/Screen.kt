package edu.ucne.marianelaventura_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data class Entrada(val entradaId: Int = 0) : Screen
}