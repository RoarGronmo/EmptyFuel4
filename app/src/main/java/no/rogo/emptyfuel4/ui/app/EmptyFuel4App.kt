package no.rogo.emptyfuel4.ui.app

import android.content.res.Resources
import android.graphics.drawable.shapes.Shape
import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.core.Clip
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.sp
import androidx.ui.engine.geometry.Outline
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.imageFromResource
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import no.rogo.emptyfuel4.ui.main.EmptyFuel4Status
import no.rogo.emptyfuel4.ui.main.Screen
import no.rogo.emptyfuel4.ui.main.VectorImage
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.model.LocationData
import no.rogo.emptyfuel4.ui.main.navigateTo
import no.rogo.emptyfuel4.ui.screen.overview.OverviewScreen
import no.rogo.emptyfuel4.ui.screen.station.StationsScreen
import no.rogo.emptyfuel4.ui.screen.statistics.StatisticsScreen

/**
 * Created by Roar on 18.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

val appColors = MaterialColors(
    primary = Color(0xFF1EB980.toInt()),
    surface = Color(0xFF26282F.toInt()),
    onSurface = Color.Yellow
)

val appTypography = MaterialTypography(
    /*
    h1 = TextStyle(fontFamily = FontFamily("RobotoCondensed"),
        fontWeight = FontWeight.W100,
        fontSize = 96.sp),
    h2 = TextStyle(fontFamily = FontFamily("RobotoCondensed"),
        fontWeight = FontWeight.W100,
        fontSize = 60.sp)

     */
)

@Composable
fun EmptyFuel4App(deviceLocation: LocationData)
{
    val (drawerState, onDrawerStateChange) = +state {DrawerState.Closed}

    MaterialTheme(appColors, appTypography){
        ModalDrawerLayout(drawerState = drawerState,
            onStateChange = onDrawerStateChange,
            gesturesEnabled = drawerState == DrawerState.Opened,
            drawerContent = {
                AppDrawer(
                    currentScreen = EmptyFuel4Status.currentScreen,
                    closeDrawer = {
                        onDrawerStateChange(DrawerState.Closed)
                    })

            },
            bodyContent = {
                AppContent(deviceLocation = deviceLocation){
                    onDrawerStateChange(DrawerState.Opened)
                }
            }
        )
    }
}

@Composable
private fun AppContent(deviceLocation: LocationData, openDrawer: () -> Unit) {
    Crossfade(current = EmptyFuel4Status.currentScreen) {screen: Screen ->
        Surface(color = Color.DarkGray) {
            when(screen) {
                is Screen.Stations -> StationsScreen(deviceLocation) { openDrawer() }
                is Screen.Overview -> OverviewScreen { openDrawer() }
                is Screen.Statistics -> StatisticsScreen { openDrawer() }

            }
        }

    }
}

@Composable
private fun AppDrawer(
    currentScreen: Screen,
    closeDrawer: () -> Unit
)
{
    val topImage = +imageResource(R.mipmap.empty_1024)


    Column(
        crossAxisSize = LayoutSize.Expand,
        mainAxisSize = LayoutSize.Expand
    ) {

        Surface(color = Color.DarkGray)
        {
            Column(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand)
            {
                Padding(8.dp) {
                    Container(expanded = true, height = 100.dp, width = 100.dp){
                        Clip(shape = RoundedCornerShape(4.dp)) {
                            DrawImage(image = topImage)
                        }
                    }
                }

                Padding(8.dp){
                    Text(text = "Empty Fuel 4", style = +themeTextStyle { h4 })
                }

                Padding(8.dp){
                    Text(text = "https://www.fuelpump.no", style = +themeTextStyle { subtitle2 })
                }


            }

        }

        /*
        Surface(color = Color.Green)
        {
            Padding(padding = 16.dp) {

                Surface(color = Color.DarkGray) {


                    Row {
                        /*VectorImage(
                            R.drawable.ic_baseline_store_24,
                            +themeColor { primary }
                        )*/
                        //WidthSpacer(width = 8.dp)
                        Text(text = "Empty Fuel")
                    }
                }


            }
        }*/

        Divider()
/*
        Padding(padding = 16.dp){
            Row{
                VectorImage(
                    R.drawable.ic_baseline_settings_applications_24,
                    +themeColor{primary}
                )
                WidthSpacer(width = 8.dp)
                Text(text = "Settings")
            }
        }
*/
        DrawerButton(
            icon = R.drawable.ic_baseline_store_24,
            label = "Stations",
            isSelected = currentScreen == Screen.Stations
        ){
            navigateTo(Screen.Stations)
            closeDrawer()
        }

        DrawerButton(
            icon = R.drawable.ic_baseline_map_24,
            label = "Overview",
            isSelected = currentScreen == Screen.Overview
        ){
            navigateTo(Screen.Overview)
            closeDrawer()
        }

        DrawerButton(
            icon = R.drawable.ic_baseline_format_list_numbered_24,
            label = "Statistics",
            isSelected = currentScreen == Screen.Statistics
        ){
            navigateTo(Screen.Statistics)
            closeDrawer()
        }

        Divider()

        DrawerButton(
            icon = R.drawable.ic_baseline_settings_applications_24,
            label = "Settings",
            isSelected = currentScreen == Screen.Settings
        ){
            closeDrawer()
        }

        DrawerButton(
            icon = R.drawable.ic_baseline_build_24,
            label = "Tools",
            isSelected = currentScreen == Screen.Tools
        ){
            closeDrawer()
        }

        DrawerButton(
            icon = R.drawable.ic_baseline_help_24,
            label = "Help",
            isSelected = currentScreen == Screen.Help
        ){
            closeDrawer()
        }



    }
}

@Composable
private fun DrawerButton(
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean,
    action: () -> Unit
){
    val textIconColor = if(isSelected){
        +themeColor { primary }
    } else{
        (+themeColor { onSurface }).copy(alpha = 0.6f)
    }

    val backgroundColor = if(isSelected){
        (+themeColor { primary }).copy(alpha = 0.12f)
    } else {
        +themeColor { surface }
    }

    Padding(left = 8.dp, top=8.dp, right = 8.dp) {

        Surface(
            color = backgroundColor,
            shape = RoundedCornerShape(4.dp)
        ){
            Button(
                onClick = action,
                style = TextButtonStyle()
            ){
                Row(
                    mainAxisSize = LayoutSize.Expand,
                    crossAxisAlignment = CrossAxisAlignment.Center
                ) {
                    VectorImage(
                        id = icon,
                        tint = textIconColor
                    )
                    WidthSpacer(width = 16.dp)
                    Text(
                        text = label
                    )
                }
            }
        }

    }
}




//@Preview("Menu")
@Composable
fun AppDrawerPreview(){

    val (drawerState, onDrawerStateChange) = +state {DrawerState.Closed}

    MaterialTheme(appColors, appTypography) {
        AppDrawer(
            currentScreen = Screen.Stations,
            closeDrawer = {
                onDrawerStateChange(DrawerState.Closed)
            }
        )
    }
}


