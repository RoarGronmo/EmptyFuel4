package no.rogo.emptyfuel4.ui.screen.station


import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.ScrollerPosition
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.material.surface.Card
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.semantics.SemanticsActions.Companion.OnClick
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import no.rogo.emptyfuel4.R
import no.rogo.emptyfuel4.data.imageBank
import no.rogo.emptyfuel4.data.mutableImageBank
import no.rogo.emptyfuel4.model.*
import no.rogo.emptyfuel4.ui.main.VectorImage
import no.rogo.emptyfuel4.ui.main.VectorImageButton
import java.sql.Date
import java.sql.Time
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import java.util.Calendar

/**
 * Created by Roar on 18.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Composable
fun StationsScreen(deviceLocation: LocationData, openDrawer: () -> Unit)
{

    var scrollPosition = ScrollPosition(0.px,0.px)

    var loadState = LoadStateClass(LoadStateType.Idle, "Idle")

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
                    mutableImageBank.add(
                        MutableImageItem(
                            id="${mutableImageBank.size}",
                            imageId = 0,
                            title = "${deviceLocation.lat}"
                        )
                    )
                    mutableImageBank[0].title="${deviceLocation.lng}"

                    //imageBank[1].title="${deviceLocation.lng}"
                }
            }
        }
        inflexible{
            Column(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand
            ) {
                ScrollPosWidget(scrollPosition = scrollPosition)
                //ScrollPageWidget(page = scrollPage)
            }
        }
        inflexible{
            Column(
                mainAxisSize = LayoutSize.Expand,
                crossAxisSize = LayoutSize.Expand
            ) {
                LoadStateWidget(loadState = loadState)

            }
        }

        flexible(flex = 1f){

            VerticalScroller(
                scrollerPosition = scrollerPosition,
                onScrollPositionChanged = { posX:Px, maxX:Px ->

                    scrollPosition.posX = posX
                    scrollPosition.maxX = maxX

                    scrollerPosition.value = posX  //Let the scroller scroll to this position

                    if(loadState.state == LoadStateType.Ready)
                    {
                        loadState.state = LoadStateType.Idle
                    }
                    else if((posX>maxX-50.px)&&(loadState.state == LoadStateType.Idle))
                    {
                        //var sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

                        //var ldt = LocalDateTime.now().toString()

                        //var zdt = ZonedDateTime.now().toString()

                        var systime = System.currentTimeMillis()

                        mutableImageBank.add(
                            MutableImageItem(
                                id="${mutableImageBank.size}",
                                imageId = 0,
                                title = "#: ${mutableImageBank.size}, " +
                                        "m: $maxX, p: $posX, s: ${loadState.state}, t: ${systime}"
                            )
                        )
                        Log.i("StationsScreen","adding mutable #: ${mutableImageBank.size}")
                        loadState.state = LoadStateType.Ready
                    }



                }
            ) {

                Column(
                    mainAxisSize = LayoutSize.Expand,
                    crossAxisSize = LayoutSize.Expand
                ) {
                    for(i in 0 until 1){
                        HeightSpacer(height = 16.dp)
                        mutableImageBank.forEach {mutableImageItem: MutableImageItem ->

                            StationCard(mutableImageItem)


                        }
                    }
                }

            }


        }



    }
}

@Composable
fun StationCard(mutableImageItem: MutableImageItem)
{

    val image = +imageResource(R.drawable.esso_icon)
    val image2 = +imageResource(R.drawable.yx_icon)

    Padding(4.dp)
    {
        Card (shape = RoundedCornerShape(5.dp))
        {

            Column()
            {

                var imageWidth: Int = image.width;
                var imageHeight: Int = image.height;
                var imageAspect: Double = image.width.toDouble()/image.height.toDouble()

                var justWidth: Double = 0.0
                var justHeight: Double = 0.0

                FlexRow {

                    inflexible{
                        Padding(padding = 4.dp){

                            Surface(color = Color.Blue)
                            {
                                DrawImageFitCenter(
                                    image = image,
                                    containerWidthDp = 50,
                                    containerHeightDp = 30
                                )
                            }

                        }

                    }

                    inflexible{
                        Padding(padding = 4.dp){

                            Surface(color = Color.Green) {
                                DrawImageFitCenter(
                                    image = image,
                                    containerWidthDp = 30,
                                    containerHeightDp = 50
                                )
                            }


                        }

                    }
                    inflexible{
                        Padding(padding = 4.dp){

                            Surface(color = Color.Yellow) {
                                DrawImageFitCenter(
                                    image = image2,
                                    containerWidthDp = 50,
                                    containerHeightDp = 30
                                )

                            }


                        }

                    }
                    inflexible{
                        Padding(padding = 4.dp){

                            Surface(color = Color.White) {
                                DrawImageFitCenter(
                                    image = image2,
                                    containerWidthDp = 30,
                                    containerHeightDp = 50
                                )
                            }


                        }

                    }

              

                    expanded(1f)
                    {
                        WidthSpacer(width = 1.dp)
                    }
                    inflexible{
                        Text(text = "Right")
                    }

                }
                Divider()
                FlexRow {

                    inflexible {
                        //Text(text = mutableImageItem.title?:"<Empty>")
                        Text(text = "Left")
                    }
                    inflexible {
                        Column {
                            Text(text = "w=${imageWidth} h=${imageHeight}" +
                                    " aspect=${imageAspect}")
                            Text(text = "jw=${justWidth} jh=${justHeight}")



                        }
                        //Text(text = mutableImageItem.title?:"<Empty>")

                    }

                    expanded(1f)
                    {
                        WidthSpacer(width = 1.dp)
                    }
                    inflexible{
                        Text(text = "Right")
                    }



                }
                FlexRow{

                }
            }
        }
    }


}

@Composable

/*
    Draws an Image in a sized container with image's aspectratio not altered,
    as in xml's android:scaleType="fitCenter"
 */

fun DrawImageFitCenter(
    image: Image,
    tint: Color? = null,
    containerWidthDp: Int,
    containerHeightDp: Int
){

    val imageWidth: Int = image.width
    val imageHeight: Int = image.height
    val imageAspect: Double = imageWidth.toDouble()/imageHeight.toDouble()
    val containerAspect: Double = containerWidthDp.toDouble()/containerHeightDp.toDouble()

    var justWidth: Double = 0.0
    var justHeight: Double = 0.0

    Container(
        width = containerWidthDp.dp,
        height = containerHeightDp.dp
    ) {
        if(containerAspect>imageAspect)
        {
            justWidth = containerWidthDp.toDouble()*(imageAspect/containerAspect)
            justHeight = containerHeightDp.toDouble()
        } else {
            justWidth = containerWidthDp.toDouble()
            justHeight = containerHeightDp.toDouble()*(containerAspect/imageAspect)
        }


        Log.i("DrawImageFitCenter","imageWidth: ${imageWidth}")
        Log.i("DrawImageFitCenter","imageHeight: ${imageHeight}")
        Log.i("DrawImageFitCenter","imageAspect: ${imageAspect}")
        Log.i("DrawImageFitCenter","containerWidthDp: ${containerWidthDp}")
        Log.i("DrawImageFitCenter","containerHeightDp: ${containerHeightDp}")
        Log.i("DrawImageFitCenter","containerAspect: ${containerAspect}")
        Log.i("DrawImageFitCenter","justWidth: ${justWidth}")
        Log.i("DrawImageFitCenter","justHeight: ${justHeight}")

        Container(
            width = justWidth.dp,
            height = justHeight.dp
        ) {
            DrawImage(
                image = image,
                tint = tint
            )
        }

    }
}


/*
@Composable
fun VerticalPagedScroller(
    onNextPage: (page: Int) -> Unit,
    child: @Composable() () -> Unit
){
    var currentPage = 0;

    val scrollerPosition: ScrollerPosition = +memo { ScrollerPosition() }

    VerticalScroller (
        scrollerPosition = scrollerPosition,
        onScrollPositionChanged = {pos: Px, maxPos: Px ->

            scrollerPosition.value = pos

            if(pos.value > maxPos.value-500){
                onNextPage(currentPage)
                currentPage++
            }
        }
    ){

        child()
    }
}

*/




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

fun LoadStateWidget(loadState: LoadStateClass){
    Surface {
        Padding(padding = 8.dp){
            Text(text = "loadState = ${loadState.state}")
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


//------------------


@Preview("StationCard")
@Composable
fun PreviewStationsCard()
{
    TemplatePreviewStationsCard()
}

@Composable
fun TemplatePreviewStationsCard(
    mutableImageItem: MutableImageItem = MutableImageItem(
        id = "0",
        title = "Title String",
        url = "URL string",
        imageId = 0,
        image = null
    )
)
{
    MaterialTheme {
        StationCard(mutableImageItem = mutableImageItem)
    }

}



//@Preview("Stations Screen")
@Composable
fun PreviewStationsScreen()
{

    TemplatePreviewStationsScreen(){

    }

}


@Composable
fun TemplatePreviewStationsScreen(
    deviceLocation:LocationData = LocationData(61.89, 6.67),
    openDrawer: () -> Unit
)
{
    MaterialTheme {
        StationsScreen(deviceLocation = deviceLocation ) {openDrawer()}
    }

}