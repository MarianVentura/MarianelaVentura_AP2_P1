package edu.ucne.marianelaventura_ap2_p1.domain.usecase

import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import edu.ucne.marianelaventura_ap2_p1.domain.repository.EntradaHuacalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEntradasUseCase @Inject constructor(
    private val repository: EntradaHuacalRepository
) {
    operator fun invoke(): Flow<List<EntradaHuacal>> = repository.getAll()
}

