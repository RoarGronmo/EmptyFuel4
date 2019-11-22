package no.rogo.emptyfuel4.ui.main

import androidx.compose.Model

/**
 * Created by Roar on 18.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

sealed class Screen{
    object Stations : Screen()
    object Overview: Screen()
    object Statistics: Screen()
    object News: Screen()
    data class Station (val stationId: String) : Screen()
    object Settings: Screen()
    object Help: Screen()
    object Tools: Screen()
}

@Model
object EmptyFuel4Status{
    var currentScreen: Screen = Screen.Stations
}

fun navigateTo(destination: Screen){
    EmptyFuel4Status.currentScreen = destination
}