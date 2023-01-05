package com.acme.cabconnect.presentation

// alle möglichen Screens für das Routing
sealed class Screen(val route: String) {
    object SuchformularScreen: Screen("suchformular_screen")
    object SuchergebnisseScreen: Screen("suchergebnisse_screen")
    object FahrtErstellenScreen: Screen("fahrt_erstellen_screen")
    object MeineFahrtenScreen: Screen("meine_fahrten_screen")
    object NachrichtenScreen: Screen("nachrichten_screen")
}
