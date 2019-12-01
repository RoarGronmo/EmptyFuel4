package no.rogo.emptyfuel4.model

import androidx.compose.Model
import androidx.ui.graphics.Image

/**
 * Created by Roar on 25.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Model
data class MutableImageItem(
    var id: String,
    var title: String?=null,
    var url: String?=null,
    var imageId: Int,
    var image: Image? = null
)

