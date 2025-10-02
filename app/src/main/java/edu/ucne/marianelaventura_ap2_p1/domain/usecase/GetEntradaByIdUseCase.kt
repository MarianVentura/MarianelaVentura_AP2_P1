package edu.ucne.marianelaventura_ap2_p1.domain.usecase

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import javax.inject.Inject

class GetEntradaByIdUseCase @Inject constructor(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(id: Int): EntradaHuacal? = repository.getById(id)
}