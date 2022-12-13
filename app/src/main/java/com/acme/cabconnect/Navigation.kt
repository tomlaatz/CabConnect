package com.acme.cabconnect

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.presentation.fahrten.*

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SuchformularScreen.route
    ) {
        composable(route = Screen.SuchformularScreen.route) {
            SuchformularScreen(navController = navController)
        }
        composable(
            route = Screen.SuchergebnisseScreen.route +
                    "?datum={datum}&start={start}&ziel={ziel}&freierPlatz={freierPlatz}",
            arguments = listOf(
                navArgument(
                    name = "datum"
                ) {
                    type = NavType.LongType
                },
                navArgument(
                    name = "start"
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = "ziel"
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = "freierPlatz"
                ) {
                    type = NavType.IntType
                },
            )
        ) {
            val datum = it.arguments?.getLong("datum") ?: System.currentTimeMillis()
            val start = it.arguments?.getString("start") ?: ""
            val ziel = it.arguments?.getString("ziel") ?: ""
            val freierPlatz = it.arguments?.getInt("freierPlatz") ?: 1
            SuchergebnisseScreen(
                navController = navController,
                datum = datum,
                start = start,
                ziel = ziel,
                freierPlatz = freierPlatz
            )
        }
        composable(route = Screen.MeineFahrtenScreen.route) {
            MeineFahrtenScreen(navController = navController)
        }
        composable(route = Screen.FahrtErstellenScreen.route) {
            ErstellenScreen(navController = navController)
        }
        composable(route = Screen.NachrichtenScreen.route) {
            NachrichtenScreen(navController = navController)
        }
    }
}