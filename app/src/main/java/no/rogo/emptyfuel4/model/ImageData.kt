package no.rogo.emptyfuel4.model

import androidx.compose.Model
import androidx.ui.graphics.Image

/**
 * Created by Roar on 20.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */


@Model
data class ImageItem(
    val id: String,
    val title: String?=null,
    val url: String?=null,
    val imageId: Int,
    val image: Image? = null
)

@Model
data class LoadStateClass(
    var state: LoadStateType,
    var message: String="")

enum class LoadStateType {
    Idle,
    Ready,
    Reading,
    Error,
    Warning,
    Done
}



