package edu.ucne.marianelaventura_ap2_p1.domain.usecase

import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import javax.inject.Inject

class DeleteEntradaUseCase @Inject constructor(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(idEntrada: Int) {
        repository.deleteById(idEntrada)
    }
}