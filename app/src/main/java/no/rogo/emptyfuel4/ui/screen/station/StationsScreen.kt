package no.rogo.emptyfuel4.ui.screen.station

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.ui.core.Px
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.px
import androidx.ui.foundation.ScrollerPosition
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Surface
import androidx.ui.semantics.SemanticsActions.Companion.OnClick
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.data.imageBank
import no.rogo.emptyfuel4.model.ImageItem
import no.rogo.emptyfuel4.model.LocationData
import no.rogo.emptyfuel4.model.ScrollPosition
import no.rogo.emptyfuel4.ui.main.VectorImageButton

/**
 * Created by Roar on 18.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Composable
fun StationsScreen(deviceLocation: LocationData, openDrawer: () -> Unit)
{

    var scrollPosition = ScrollPosition(0.px,0.px)

    var scrollerPosition = +memo{ ScrollerPosition ()}

    FlexColumn {
        inflexible {
            TopAppBar(
                title = {Text(text = "Stations")},
                navigationIcon = {
                    VectorImageButton(id = R.drawable.ic_baseline_menu_24) {
                        openDrawer()
                    }
                }
            )
        }
        inflexible {
            Column (
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand
            ){

                LocationWidget(deviceLocation)
            }
        }
        inflexible {
            Column(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand
            ){
                PushWidget(){
                    deviceLocation.lat++
                    deviceLocation.lng++
                }
            }
        }
        inflexible{
            Column(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand
            ) {
                ScrollPosWidget(scrollPosition = scrollPosition)
            }
        }
        flexible(flex = 1f)
        {
            VerticalScroller (
                scrollerPosition = scrollerPosition,
                onScrollPositionChanged = { px: Px, px1: Px ->
                    scrollPosition.posX = px
                    scrollPosition.maxX = px1
                    scrollerPosition.value = px

            }){

                Column {

                    for(i in 0..20) {
                        HeightSpacer(16.dp)
                        imageBank.forEach { imageItem: ImageItem ->

                            Text(text = imageItem.title ?: "<Empty>")

                            Divider()
                        }
                    }
                }
            }
        }
    }
}



val scrollPosChng = {px: Px, px1: Px ->

}

@Composable
fun VertScrollPosChanged(){

}

@Composable
fun LocationWidget(locationData: LocationData){

    Surface {
        Padding(padding = 8.dp) {
            Text(text = "${locationData.lat}, ${locationData.lng}")
        }
    }

}

@Composable
fun PushWidget(action: () -> Unit){

    Surface {
        Padding(padding = 8.dp) {
            Button(text = "Click Me!", onClick = action)
        }
    }
}

@Composable
fun ScrollPosWidget(scrollPosition: ScrollPosition){
    Surface {
        Padding(padding = 8.dp) {
            Text(text = "posX=${scrollPosition.posX}, maxX=${scrollPosition.maxX}")
        }
    }
}

@Composable
fun StationsWidget()
{
    VerticalScroller {
        
    }
}