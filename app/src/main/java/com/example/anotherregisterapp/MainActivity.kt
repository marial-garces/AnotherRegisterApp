package com.example.anotherregisterapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anotherregisterapp.Routes.DASHBOARD
import com.example.anotherregisterapp.Routes.JOIN_US
import com.example.anotherregisterapp.Routes.LOGIN
import com.example.anotherregisterapp.Routes.PROFILE
import com.example.anotherregisterapp.screens.DashboardScreen
import com.example.anotherregisterapp.screens.JoinUsScreen
import com.example.anotherregisterapp.screens.ProfileScreen
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
                       composable(JOIN_US) { JoinUsScreen(navController = navController) }

                        composable(DASHBOARD) { DashboardScreen(navController = navController) }

                        composable(PROFILE) { ProfileScreen(navController = navController) }



//                        composable(LOGIN) { LoginScreen(navController = navController) }
//
//                        composable(Routes.REGISTER) { RegisterScreen(navController = navController)}
                    }
                )


            }
        }
    }
}

