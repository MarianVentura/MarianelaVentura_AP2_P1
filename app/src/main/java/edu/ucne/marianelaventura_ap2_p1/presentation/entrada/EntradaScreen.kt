package edu.ucne.marianelaventura_ap2_p1.presentation.entrada

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.marianelaventura_ap2_p1.domain.model.EntradaHuacal
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradaScreen(viewModel: EntradaViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    var showWelcome by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = showWelcome,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        WelcomeScreen(
            onStartRegistration = { showWelcome = false }
        )
    }

    AnimatedVisibility(
        visible = !showWelcome,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
    ) {
        MainEntradaScreen(
            state = state,
            viewModel = viewModel,
            onBackToWelcome = { showWelcome = true }
        )
    }
}

@Composable
fun WelcomeScreen(onStartRegistration: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF388E3C),
                        Color(0xFF81C784)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50).copy(alpha = 0.2f),
                                    Color(0xFF4CAF50).copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        )
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFF4CAF50)
                    )
                }

                Text(
                    text = "Control de Huacales",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Color(0xFF2C2C2C)
                )

                Text(
                    text = "Registro y administración de entradas de huacales",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Registra nuevas entradas, actualiza cantidades y precios, y mantiene un control completo de tus huacales.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF444444),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartRegistration,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Text("Gestionar Entradas", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainEntradaScreen(
    state: EntradaUiState,
    viewModel: EntradaViewModel,
    onBackToWelcome: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro de Huacales", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackToWelcome) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio")
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item { EntradaForm(state = state, onEvent = viewModel::onEvent) }

            item {
                Text(
                    text = "Entradas Registradas (${state.entradas.size})",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (state.entradas.isEmpty()) {
                item { EmptyEntradasCard() }
            } else {
                items(state.entradas) { entrada ->
                    EntradaItem(
                        entrada = entrada,
                        onEdit = { viewModel.onEvent(EntradaEvent.SelectEntrada(entrada)) },
                        onDelete = { viewModel.onEvent(EntradaEvent.DeleteEntrada(entrada.idEntrada)) }
                    )
                }
            }
        }
    }
}

@Composable
fun EntradaForm(state: EntradaUiState, onEvent: (EntradaEvent) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (state.isEditing) "Editar Entrada" else "Nueva Entrada",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF4CAF50)
            )

            // Nombre Cliente
            OutlinedTextField(
                value = state.nombreCliente,
                onValueChange = { onEvent(EntradaEvent.NombreClienteChanged(it)) },
                label = { Text("Nombre del Cliente") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF4CAF50)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = state.nombreClienteError != null,
                supportingText = { state.nombreClienteError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )

            // Cantidad
            OutlinedTextField(
                value = state.cantidad,
                onValueChange = { onEvent(EntradaEvent.CantidadChanged(it)) },
                label = { Text("Cantidad de Huacales") },
                leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null, tint = Color(0xFF4CAF50)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = state.cantidadError != null,
                supportingText = { state.cantidadError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )


            // Precio
            OutlinedTextField(
                value = state.cantidad,
                onValueChange = { onEvent(EntradaEvent.CantidadChanged(it)) },
                label = { Text("Precio") },
                leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null, tint = Color(0xFF4CAF50)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = state.cantidadError != null,
                supportingText = { state.cantidadError?.let { Text(it, color = MaterialTheme.colorScheme.error) } }
            )


            // Botones
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { onEvent(EntradaEvent.SaveEntrada) },
                    modifier = Modifier.weight(1f).height(48.dp),
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                            Text(if (state.isEditing) "Actualizar" else "Guardar")
                        }
                    }
                }

                OutlinedButton(
                    onClick = { onEvent(EntradaEvent.ClearForm) },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        Text("Limpiar")
                    }
                }
            }

            // Mensajes
            AnimatedVisibility(visible = state.successMessage != null, enter = slideInVertically() + fadeIn(), exit = slideOutVertically() + fadeOut()) {
                state.successMessage?.let { message ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50))
                            Text(message, color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            AnimatedVisibility(visible = state.errorMessage != null, enter = slideInVertically() + fadeIn(), exit = slideOutVertically() + fadeOut()) {
                state.errorMessage?.let { message ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Text(message, color = MaterialTheme.colorScheme.onErrorContainer, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EntradaItem(
    entrada: EntradaHuacal,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(56.dp)
                        .background(brush = Brush.radialGradient(colors = listOf(Color(0xFF4CAF50).copy(alpha = 0.8f), Color(0xFF388E3C))), shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Inventory, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                }

                Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                    Text(entrada.nombreCliente, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color(0xFF2C2C2C))
                    Text("ID: ${entrada.idEntrada}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF666666))
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onEdit, modifier = Modifier.background(color = Color(0xFF2196F3).copy(alpha = 0.1f), shape = CircleShape)) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF2196F3))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.background(color = Color(0xFFF44336).copy(alpha = 0.1f), shape = CircleShape)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFF44336))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Cantidad: ${entrada.cantidad} | Precio: ${entrada.precio}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF444444),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun EmptyEntradasCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFF9E9E9E))
            Text("No hay entradas registradas", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold), color = Color(0xFF666666))
            Text("Completa el formulario de arriba para agregar tu primera entrada", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF9E9E9E), textAlign = TextAlign.Center)
        }
    }
}

// Previews
@Preview(showSystemUi = true)
@Composable
fun EntradaWelcomeScreenPreview() {
    MaterialTheme { WelcomeScreen(onStartRegistration = {}) }
}

@Preview(showBackground = true)
@Composable
fun EmptyEntradasCardPreview() {
    MaterialTheme { EmptyEntradasCard() }
}

@Preview(showBackground = true)
@Composable
fun EntradaItemPreview() {
    MaterialTheme {
        EntradaItem(
            entrada = EntradaHuacal(idEntrada = 1, nombreCliente = "Cliente Prueba", cantidad = 10, precio = 150.0),
            onEdit = {},
            onDelete = {}
        )
    }
}
