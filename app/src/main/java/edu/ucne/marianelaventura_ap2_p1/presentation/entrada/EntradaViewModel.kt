package edu.ucne.marianelaventura_ap2_p1.presentation.entrada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import edu.ucne.marianelaventura_ap2_p1.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EntradaViewModel @Inject constructor(
    private val getEntradasUseCase: GetEntradasUseCase,
    private val getEntradaByIdUseCase: GetEntradaByIdUseCase,
    private val upsertEntradaUseCase: UpsertEntradaUseCase,
    private val deleteEntradaUseCase: DeleteEntradaUseCase,
    private val validateEntradaUseCase: ValidateEntradaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntradaUiState())
    val uiState: StateFlow<EntradaUiState> = _uiState.asStateFlow()

    init {
        loadEntradas()
    }

    fun onEvent(event: EntradaEvent) {
        when (event) {
            is EntradaEvent.NombreClienteChanged -> {
                _uiState.update {
                    it.copy(
                        nombreCliente = event.nombreCliente,
                        nombreClienteError = null,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }

            is EntradaEvent.CantidadChanged -> {
                _uiState.update {
                    it.copy(
                        cantidad = event.cantidad,
                        cantidadError = null,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }

            is EntradaEvent.PrecioChanged -> {
                _uiState.update {
                    it.copy(
                        precio = event.precio,
                        precioError = null,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }

            EntradaEvent.SaveEntrada -> saveEntrada()
            EntradaEvent.ClearForm -> clearForm()
            is EntradaEvent.DeleteEntrada -> deleteEntrada(event.idEntrada)
            is EntradaEvent.SelectEntrada -> selectEntrada(event.entrada)
        }
    }

    private fun loadEntradas() {
        viewModelScope.launch {
            getEntradasUseCase()
                .catch { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error al cargar entradas: ${exception.message}"
                        )
                    }
                }
                .collect { entradas ->
                    _uiState.update {
                        it.copy(
                            entradas = entradas,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun loadEntrada(idEntrada: Int) {
        viewModelScope.launch {
            try {
                val entrada = getEntradaByIdUseCase(idEntrada)
                if (entrada != null) {
                    _uiState.update {
                        it.copy(
                            idEntrada = entrada.idEntrada,
                            fecha = entrada.fecha,
                            nombreCliente = entrada.nombreCliente,
                            cantidad = entrada.cantidad.toString(),
                            precio = entrada.precio.toString(),
                            isEditing = true,
                            nombreClienteError = null,
                            cantidadError = null,
                            precioError = null,
                            errorMessage = null,
                            successMessage = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Error al cargar entrada: ${e.message}"
                    )
                }
            }
        }
    }

    private fun saveEntrada() {
        viewModelScope.launch {
            val state = _uiState.value

            val nombreError = validateEntradaUseCase.validateNombreCliente(state.nombreCliente)
            val cantidadError = validateEntradaUseCase.validateCantidad(state.cantidad)
            val precioError = validateEntradaUseCase.validatePrecio(state.precio)

            if (nombreError != null || cantidadError != null || precioError != null) {
                _uiState.update {
                    it.copy(
                        nombreClienteError = nombreError,
                        cantidadError = cantidadError,
                        precioError = precioError,
                        isLoading = false
                    )
                }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true) }

            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            val entrada = EntradaHuacal(
                idEntrada = state.idEntrada,
                fecha = fechaActual,
                nombreCliente = state.nombreCliente.trim(),
                cantidad = state.cantidad.toInt(),
                precio = state.precio.toDouble()
            )

            upsertEntradaUseCase(entrada)
                .onSuccess {
                    val message = if (state.isEditing) {
                        "Entrada actualizada exitosamente"
                    } else {
                        "Entrada guardada exitosamente"
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successMessage = message,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Error desconocido",
                            successMessage = null
                        )
                    }
                }
        }
    }

    private fun deleteEntrada(idEntrada: Int) {
        viewModelScope.launch {
            try {
                deleteEntradaUseCase(idEntrada)
                _uiState.update {
                    it.copy(
                        successMessage = "Entrada eliminada exitosamente",
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Error al eliminar entrada: ${e.message}",
                        successMessage = null
                    )
                }
            }
        }
    }

    private fun selectEntrada(entrada: EntradaHuacal) {
        _uiState.update {
            it.copy(
                idEntrada = entrada.idEntrada,
                fecha = entrada.fecha,
                nombreCliente = entrada.nombreCliente,
                cantidad = entrada.cantidad.toString(),
                precio = entrada.precio.toString(),
                nombreClienteError = null,
                cantidadError = null,
                precioError = null,
                errorMessage = null,
                successMessage = null,
                isEditing = true
            )
        }
    }

    private fun clearForm() {
        _uiState.update { state ->
            EntradaUiState(
                entradas = state.entradas
            )
        }
    }
}