package no.rogo.emptyfuel4.ui.screen.station


import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.ui.core.*
import androidx.ui.engine.geometry.Offset
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.ScrollerPosition
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.graphics.Shadow
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Card
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.semantics.SemanticsActions.Companion.OnClick
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextOverflow
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

    val card_background_color =
        Color(
            ContextCompat.getColor(
                +ambient(ContextAmbient),
                R.color.card_background_lightgreen
            )
        )

    val image = +imageResource(R.drawable.yx_icon)
    val imagekind = +imageResource(R.drawable.ic_empty_kind_24dp_black)

    Padding(4.dp)
    {
        Card (shape = RoundedCornerShape(5.dp),
            color = card_background_color)
        {
            Column()
            {
                FlexRow {

                    inflexible{
                        Padding(padding = 4.dp){
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(
                                    ContextCompat.getColor(
                                        +ambient(ContextAmbient),
                                        R.color.enterprise_icon_background_color)
                                )
                            ) {
                                Padding(padding = 4.dp) {
                                    DrawImageFitCenter(
                                        image = image,
                                        containerWidth = 40.dp,
                                        containerHeight = 40.dp
                                    )
                                }
                            }
                        }
                    }

                    flexible(1f) {
                        Column(
                            mainAxisSize = LayoutSize.Expand,
                            crossAxisSize = LayoutSize.Expand
                        ) {
                            Surface(color = Color.Transparent) {
                                Text(
                                    text = "Station Name",
                                    style = +themeTextStyle { h6 },
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis)
                                //Text(text = "Station Address",style = (+themeTextStyle { subtitle2 }).withOpacity(0.6f))
                            }
                            Surface(color = Color.Transparent){
                                Text(
                                    text = "Station Address, 0000 Location",
                                    style = (+themeTextStyle { subtitle2 }).withOpacity(0.6f),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis)
                            }


                        }

                    }

                    inflexible{

                        Padding(right = 4.dp) {
                            Column(
                                mainAxisSize = LayoutSize.Expand,
                                crossAxisSize = LayoutSize.Expand,
                                crossAxisAlignment = CrossAxisAlignment.End
                            ) {
                                Surface(
                                    color = Color.Transparent
                                ){
                                    Text(
                                        text = "123,3 km",
                                        style = (+themeTextStyle { caption })
                                            .copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold)
                                    )
                                }
                                Surface(color = Color.Transparent) {
                                    Button(
                                        onClick = {},
                                        text = "DETAILS",
                                        style = TextButtonStyle()
                                    )
                                }
                            }
                        }
                    }
                }

                Padding(
                    left = 4.dp,
                    right = 4.dp
                ){
                    Divider()
                }

                FlexRow {

                    flexible(1f) {
                        Column(
                            mainAxisSize = LayoutSize.Expand,
                            crossAxisSize = LayoutSize.Expand,
                            crossAxisAlignment = CrossAxisAlignment.Start
                        ) {

                            Padding(left = 4.dp, right = 4.dp)
                            {

                                Text(
                                    text = "Country average",
                                    style = (+themeTextStyle { subtitle2 })
                                        .copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                        }
                    }

                    flexible(1f){
                        Column(
                            mainAxisSize = LayoutSize.Expand,
                            crossAxisSize = LayoutSize.Expand,
                            crossAxisAlignment = CrossAxisAlignment.End
                        ){

                            Padding(
                                left = 4.dp,
                                right = 4.dp
                            ) {
                                Text(
                                    text = "Uncertain availability",
                                    style = (+themeTextStyle { subtitle2 })
                                        .copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                        }
                    }
                }

                FlexRow{
                    inflexible{
                        Padding(padding = 4.dp){
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(
                                    ContextCompat.getColor(
                                        +ambient(ContextAmbient),
                                        R.color.enterprise_icon_background_color)
                                )
                            ) {
                                Padding(padding = 4.dp) {
                                    DrawImageFitCenter(
                                        image = imagekind,
                                        containerWidth = 40.dp,
                                        containerHeight = 40.dp
                                    )
                                }
                            }
                        }
                    }
                    flexible(1f) {
                        Row(
                            mainAxisSize = LayoutSize.Expand,
                            crossAxisSize = LayoutSize.Expand
                        ) {
                            Surface(color = Color.Transparent) {
                                Text(
                                    text = "15,98",
                                    style = (+themeTextStyle { h4 })
                                        .copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Yellow,
                                            shadow = Shadow(
                                                color = Color.Gray,
                                                offset = Offset(
                                                    dx=5f,
                                                    dy=5f
                                                ),
                                                blurRadius = 2.px
                                            )
                                        ),
                                    maxLines = 1)
                                //Text(text = "Station Address",style = (+themeTextStyle { subtitle2 }).withOpacity(0.6f))
                            }

                            Padding(left = 4.dp,top = 4.dp)
                            {
                                Column {
                                    Text(
                                        text = "NOK",
                                        style = (+themeTextStyle { caption })
                                            .copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                    )
                                    Text(
                                        text = "ltr",
                                        style = (+themeTextStyle { caption })
                                            .copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                    )
                                }
                            }
                        }
                    }
                    inflexible {
                        Padding(top = 4.dp, right = 4.dp) {

                            Column(mainAxisSize = LayoutSize.Expand,
                                crossAxisSize = LayoutSize.Expand,
                                crossAxisAlignment = CrossAxisAlignment.End
                            ) {
                                Button(onClick = {},
                                    style = ContainedButtonStyle()
                                ){
                                    Row(mainAxisSize = LayoutSize.Expand,
                                        crossAxisAlignment = CrossAxisAlignment.Center
                                    ){
                                        VectorImage(id = R.drawable.ic_baseline_edit_24)
                                        WidthSpacer(width = 16.dp)
                                        Text(
                                            text = "UPDATE",
                                            style = +themeTextStyle { button })
                                    }
                                }

                            }
                        }
                    }
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
    containerWidth: Dp,
    containerHeight: Dp
){

    //val imageWidth = image.width.dp
    //val imageHeight = image.height.dp
    val imageAspect = image.width.dp.div(image.height.dp)
    val containerAspect = containerWidth.div(containerHeight)

    var justWidth = 0.0.dp
    var justHeight = 0.0.dp

    Container(
        width = containerWidth,
        height = containerHeight
    ) {
        if(containerAspect>imageAspect)
        {
            justWidth = containerWidth.times(imageAspect/containerAspect)
            justHeight = containerHeight
        } else {
            justWidth = containerWidth
            justHeight = containerHeight.times(containerAspect/imageAspect)
        }

        Log.i("DrawImageFitCenter","imageWidth: ${image.width.dp}")
        Log.i("DrawImageFitCenter","imageHeight: ${image.height.dp}")
        Log.i("DrawImageFitCenter","imageAspect: ${imageAspect}")
        Log.i("DrawImageFitCenter","containerWidthDp: ${containerWidth}")
        Log.i("DrawImageFitCenter","containerHeightDp: ${containerHeight}")
        Log.i("DrawImageFitCenter","containerAspect: ${containerAspect}")
        Log.i("DrawImageFitCenter","justWidth: ${justWidth}")
        Log.i("DrawImageFitCenter","justHeight: ${justHeight}")

        Container(
            width = justWidth,
            height = justHeight
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