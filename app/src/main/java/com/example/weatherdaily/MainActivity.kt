package com.example.weatherdaily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weatherdaily.ui.navigation.WeatherApp
import com.example.weatherdaily.ui.theme.WeatherDailyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            WeatherDailyTheme {
                WeatherApp()
            }
        }
    }
}
