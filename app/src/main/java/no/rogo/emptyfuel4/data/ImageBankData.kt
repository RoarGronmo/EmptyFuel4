package no.rogo.emptyfuel4.data

import android.content.res.Resources
import androidx.ui.graphics.imageFromResource
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.model.ImageItem

/**
 * Created by Roar on 20.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */


val imageItem1 = ImageItem(
    "1",
    "EmptyFuel 4",
    "http://www.fuelpump.no",
    R.mipmap.empty_1024
)

val imageItem2 = ImageItem(
    "A",
    "Esso",
    "http://www.esso.no",
    R.drawable.esso_icon
)


val imageItem3 = ImageItem(
    "B",
    "YX",
    "http://www.yx.no",
    R.drawable.yx_icon
)


var imageBank = listOf(
    imageItem1,
    imageItem2,
    imageItem3
)



fun getImageBank(imageBank: List<ImageItem>, resources: Resources): List<ImageItem>{
    return imageBank.map {
        it.copy( image = imageFromResource(resources, it.imageId))
    }
}