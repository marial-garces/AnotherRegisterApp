package com.example.anotherregisterapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anotherregisterapp.Routes.LOGIN
import com.example.anotherregisterapp.screens.DashboardScreen
import com.example.anotherregisterapp.screens.LoginScreen
import com.example.anotherregisterapp.screens.ProfileScreen
import com.example.anotherregisterapp.screens.RegisterScreen
import com.example.anotherregisterapp.ui.theme.AnotherRegisterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnotherRegisterAppTheme {
                val navController = rememberNavController()


                NavHost(
                    navController = navController,
                    startDestination = LOGIN,
                    builder = {
                        composable(LOGIN) { LoginScreen(navController = navController) }

                        composable(Routes.REGISTER) { RegisterScreen(navController = navController)}

                        composable(Routes.DASHBOARD) { DashboardScreen(navController = navController) }

                        composable(Routes.PROFILE) { ProfileScreen(navController = navController) }
                    }
                )


            }
        }
    }
}

