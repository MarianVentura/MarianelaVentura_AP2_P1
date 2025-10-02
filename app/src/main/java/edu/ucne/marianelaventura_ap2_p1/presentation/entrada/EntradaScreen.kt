package edu.ucne.marianelaventura_ap2_p1.presentation.entrada

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EntradaScreen(
    entradaId: Int = 0,
    viewModel: EntradaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(entradaId) {
        if (entradaId > 0) {
            viewModel.loadEntrada(entradaId)
        }
    }

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            kotlinx.coroutines.delay(1500)
            onNavigateBack()
        }
    }

    EntradaScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradaScreenContent(
    state: EntradaUiState,
    onEvent: (EntradaEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isEditing) "Editar Entrada" else "Nueva Entrada",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (state.isEditing) "Actualizar Información" else "Registrar Nueva Entrada",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF4CAF50)
                    )

                    Text(
                        text = "Complete todos los campos para ${if (state.isEditing) "actualizar" else "registrar"} la entrada",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    // Campo Fecha (NUEVO - EDITABLE)
                    OutlinedTextField(
                        value = state.fecha,
                        onValueChange = { onEvent(EntradaEvent.FechaChanged(it)) },
                        label = { Text("Fecha") },
                        placeholder = { Text("Ej: 15/01/2025") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = state.fechaError != null,
                        supportingText = {
                            state.fechaError?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.nombreCliente,
                        onValueChange = { onEvent(EntradaEvent.NombreClienteChanged(it)) },
                        label = { Text("Nombre del Cliente") },
                        placeholder = { Text("Ej: Juan Pérez") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = state.nombreClienteError != null,
                        supportingText = {
                            state.nombreClienteError?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.cantidad,
                        onValueChange = { onEvent(EntradaEvent.CantidadChanged(it)) },
                        label = { Text("Cantidad de Huacales") },
                        placeholder = { Text("Ej: 50") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Inventory,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = state.cantidadError != null,
                        supportingText = {
                            state.cantidadError?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.precio,
                        onValueChange = { onEvent(EntradaEvent.PrecioChanged(it)) },
                        label = { Text("Precio") },
                        placeholder = { Text("Ej: 150.00") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.AttachMoney,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = state.precioError != null,
                        supportingText = {
                            state.precioError?.let {
                                Text(it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { onEvent(EntradaEvent.SaveEntrada) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                enabled = !state.isLoading,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF5C6BC0)
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp
                                )
                            ) {
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        "Guardar",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            if (state.isEditing) {
                                OutlinedButton(
                                    onClick = {
                                        onEvent(EntradaEvent.DeleteEntrada(state.idEntrada))
                                        onNavigateBack()
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    enabled = !state.isLoading,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFFF44336)
                                    )
                                ) {
                                    Text(
                                        "Eliminar",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        OutlinedButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            enabled = !state.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Cancelar",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = state.successMessage != null,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                state.successMessage?.let { message ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                message,
                                color = Color(0xFF2E7D32),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = state.errorMessage != null,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                state.errorMessage?.let { message ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(
                                alpha = 0.1f
                            )
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                message,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EntradaScreenNewPreview() {
    MaterialTheme {
        EntradaScreenContent(
            state = EntradaUiState(
                idEntrada = 0,
                nombreCliente = "",
                cantidad = "",
                precio = "",
                isEditing = false
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

// Preview del formulario Editar
@Preview(showSystemUi = true)
@Composable
fun EntradaScreenEditPreview() {
    MaterialTheme {
        EntradaScreenContent(
            state = EntradaUiState(
                idEntrada = 1,
                fecha = "01/10/2025",
                nombreCliente = "Roronoa Zoro",
                cantidad = "50",
                precio = "150.00",
                isEditing = true
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}

// Preview del formulario con errores para la validación
@Preview(showSystemUi = true)
@Composable
fun EntradaScreenWithErrorsPreview() {
    MaterialTheme {
        EntradaScreenContent(
            state = EntradaUiState(
                nombreCliente = "Zo",
                cantidad = "-5",
                precio = "abc",
                nombreClienteError = "El nombre debe tener al menos 3 caracteres",
                cantidadError = "La cantidad debe ser mayor a 0",
                precioError = "El precio debe ser un número válido",
                isEditing = false
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}


// Preview con mensaje de éxito
@Preview(showSystemUi = true)
@Composable
fun EntradaScreenSuccessPreview() {
    MaterialTheme {
        EntradaScreenContent(
            state = EntradaUiState(
                nombreCliente = "Roronoa Zoro",
                cantidad = "50",
                precio = "150.00",
                successMessage = "Entrada guardada exitosamente",
                isEditing = false
            ),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}