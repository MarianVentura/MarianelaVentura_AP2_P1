package edu.ucne.marianelaventura_ap2_p1.domain.usecase

import javax.inject.Inject

class ValidateEntradaUseCase @Inject constructor() {

    fun validateNombreCliente(nombre: String): String? {
        return when {
            nombre.isBlank() -> "El nombre del cliente es obligatorio"
            nombre.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    fun validateCantidad(cantidad: String): String? {
        return when {
            cantidad.isBlank() -> "La cantidad es obligatoria"
            cantidad.toIntOrNull() == null -> "La cantidad debe ser un número válido"
            cantidad.toInt() <= 0 -> "La cantidad debe ser mayor a 0"
            cantidad.toInt() > 10000 -> "La cantidad no puede exceder 10,000"
            else -> null
        }
    }

    fun validatePrecio(precio: String): String? {
        return when {
            precio.isBlank() -> "El precio es obligatorio"
            precio.toDoubleOrNull() == null -> "El precio debe ser un número válido"
            precio.toDouble() <= 0 -> "El precio debe ser mayor a 0"
            precio.toDouble() > 1000000 -> "El precio no puede exceder 1,000,000"
            else -> null
        }
    }
}