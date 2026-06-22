package com.example.busdieuhanhdongnai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.busdieuhanhdongnai.navigation.AppNavGraph
import com.example.busdieuhanhdongnai.ui.theme.BusDieuHanhDongNaiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BusDieuHanhDongNaiTheme {
                AppNavGraph()
            }
        }
    }
}