package edu.ucne.marianelaventura_ap2_p1.data.mappers

import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradaHuacalEntity
import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal

fun EntradaHuacalEntity.toDomain(): EntradaHuacal {
    return EntradaHuacal(
        idEntrada = idEntrada,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}

fun EntradaHuacal.toEntity(): EntradaHuacalEntity {
    return EntradaHuacalEntity(
        idEntrada = idEntrada,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}