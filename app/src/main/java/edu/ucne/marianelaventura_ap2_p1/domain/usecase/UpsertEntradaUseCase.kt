package edu.ucne.marianelaventura_ap2_p1.domain.usecase

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import javax.inject.Inject

class UpsertEntradaUseCase @Inject constructor(
    private val repository: EntradaHuacalRepository
) {
    suspend operator fun invoke(entrada: EntradaHuacal): Result<Unit> {
        return runCatching {
            repository.upsert(entrada)
        }
    }
}