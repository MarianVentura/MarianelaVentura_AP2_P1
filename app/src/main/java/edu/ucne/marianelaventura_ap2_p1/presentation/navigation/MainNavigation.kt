package edu.ucne.marianelaventura_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.marianelaventura_ap2_p1.presentation.entrada.EntradaScreen
import edu.ucne.marianelaventura_ap2_p1.presentation.home.HomeScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onNavigateToCreateEntrada = {
                    navController.navigate(Screen.Entrada(entradaId = 0))
                },
                onNavigateToEditEntrada = { entradaId ->
                    navController.navigate(Screen.Entrada(entradaId = entradaId))
                }
            )
        }

        composable<Screen.Entrada> { backStackEntry ->
            val entrada = backStackEntry.toRoute<Screen.Entrada>()
            EntradaScreen(
                entradaId = entrada.entradaId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}