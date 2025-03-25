package com.michaelbentz.qriptid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.michaelbentz.qriptid.Constants.Ui.Routes.QR_CODE_SCREEN
import com.michaelbentz.qriptid.ui.screen.QrCodeScreen
import com.michaelbentz.qriptid.ui.theme.QRiptidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            QRiptidTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = QR_CODE_SCREEN
                    ) {
                        composable(QR_CODE_SCREEN) {
                            QrCodeScreen()
                        }
                    }
                }
            }
        }
    }
}
