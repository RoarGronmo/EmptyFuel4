package no.rogo.emptyfuel4.model

import androidx.ui.graphics.Image

/**
 * Created by Roar on 20.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */



data class ImageItem(
    val id: String,
    val title: String?=null,
    val url: String?=null,
    val imageId: Int,
    val image: Image? = null
)

