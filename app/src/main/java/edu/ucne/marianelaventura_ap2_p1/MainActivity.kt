package edu.ucne.marianelaventura_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.marianelaventura_ap2_p1.presentation.navigation.MainNavigation
import edu.ucne.marianelaventura_ap2_p1.ui.theme.MarianelaVentura_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarianelaVentura_AP2_P1Theme {
                MainNavigation()
            }
        }
    }
}