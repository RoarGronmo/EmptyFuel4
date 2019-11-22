package no.rogo.emptyfuel4.ui.screen.overview

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.layout.FlexColumn
import androidx.ui.material.TopAppBar
import androidx.ui.material.themeColor
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.model.LocationData
import no.rogo.emptyfuel4.ui.main.VectorImageButton


/**
 * Created by Roar on 18.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Composable
fun OverviewScreen(openDrawer: () -> Unit) {

    FlexColumn{
        inflexible {
            TopAppBar(
                title = {Text(text = "Overview")},
                color = +themeColor { primary },
                navigationIcon = {
                    VectorImageButton(id = R.drawable.ic_baseline_menu_24){
                        openDrawer()
                    }
                }
            )
        }
    }

}


