package com.example.a2do_parcial_progra3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a2do_parcial_progra3.ui.screen.MainAppScreen
import com.example.a2do_parcial_progra3.ui.theme._2do_Parcial_Progra3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _2do_Parcial_Progra3Theme {
                MainAppScreen()
            }
        }
    }
}