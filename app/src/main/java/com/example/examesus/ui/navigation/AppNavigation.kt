package com.example.examesus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examesus.ui.screens.dashboard.DashboardScreen
import com.example.examesus.ui.screens.login.LoginScreen
import com.example.examesus.viewmodel.AuthViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val loggedUser by authViewModel.loggedUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("dashboard") {
            if (loggedUser != null) {
                DashboardScreen(
                    user = loggedUser!!,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate("login")
                    }
                )
            }
        }

    }
}
