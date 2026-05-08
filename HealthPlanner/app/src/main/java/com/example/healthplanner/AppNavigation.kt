package com.example.healthplanner

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.screen_1) {
        composable(Routes.screen_1) {
            Screen_1(navController)
        }

        composable(
            route = Routes.screen_2 + "/{resultado}",
            arguments = listOf(navArgument("resultado") { type = NavType.FloatType })
        ) { backStackEntry ->
            val resultado = backStackEntry.arguments?.getFloat("resultado") ?: 0f

            Screen_2(
                navController = navController,
                resultado = resultado
            )
        }

        composable(
            Routes.screen_3 + "/{resultado}",
            arguments = listOf(navArgument("resultado") { type = NavType.FloatType })
        ) { backStackEntry ->
            val resultado = backStackEntry.arguments?.getFloat("resultado") ?: 0f

            Screen_3(
                navController = navController,
                resultado = resultado
            )
        }


        composable(
            Routes.screen_4 + "/{listaActividades}",
            arguments = listOf(navArgument("listaActividades") { type = NavType.StringType })
        ) { backStackEntry ->
            val cadenaRecibida = backStackEntry.arguments?.getString("listaActividades") ?: ""

            val listaActividades = if (cadenaRecibida == "vacio" || cadenaRecibida.isEmpty()) {
                emptyList()
            } else {
                cadenaRecibida.split(",")
            }

            Screen_4(
                navController,
                listaActividades = listaActividades
            )
        }
    }
}

