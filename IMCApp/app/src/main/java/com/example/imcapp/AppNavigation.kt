package com.example.imcapp

import ScreenA
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.screenA) {
        composable(Routes.screenA) {
            ScreenA(navController)
        }

        composable(route = Routes.screenB + "/{resultado}") { backStackEntry ->
            val resultadoStr = backStackEntry.arguments?.getString("resultado")
            val resultado = resultadoStr?.toFloatOrNull() ?: 0f

            ScreenB(
                resultado = resultado,
                onRecalculate = {
                    navController.popBackStack()
                }
            )
        }
    }
}