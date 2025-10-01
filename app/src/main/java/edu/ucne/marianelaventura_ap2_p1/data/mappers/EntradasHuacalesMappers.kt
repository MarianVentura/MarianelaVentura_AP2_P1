package edu.ucne.marianelaventura_ap2_p1.data.mappers

import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradasHuacalesEntity
import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradasHuacales

fun EntradasHuacalesEntity.toDomain(): EntradasHuacales {
    return EntradasHuacales(
        idEntrada = idEntrada,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}

fun EntradasHuacales.toEntity(): EntradasHuacalesEntity {
    return EntradasHuacalesEntity(
        idEntrada = idEntrada,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}