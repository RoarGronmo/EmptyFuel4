package no.rogo.emptyfuel4.model

import android.location.Location
import androidx.compose.Model
import androidx.ui.core.Px

/**
 * Created by Roar on 21.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Model
data class LocationData(
    var lat: Double,
    var lng: Double
)

@Model
data class ScrollPosition(
    var posX: Px,
    var maxX: Px
)