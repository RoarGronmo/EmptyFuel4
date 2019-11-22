package no.rogo.emptyfuel4.ui.screen.statistics

import androidx.ui.core.Text
import androidx.ui.layout.FlexColumn
import androidx.ui.material.TopAppBar
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.ui.main.VectorImageButton

/**
 * Created by Roar on 19.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */
fun StatisticsScreen(openDrawer: () -> Unit)
{
    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text(text = "Statistics") },
                navigationIcon = {
                    VectorImageButton(id = R.drawable.ic_baseline_menu_24) {
                        openDrawer()
                    }
                }
            )
        }
    }
}