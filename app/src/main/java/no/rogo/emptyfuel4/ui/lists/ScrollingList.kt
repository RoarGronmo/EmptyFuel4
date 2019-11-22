package no.rogo.emptyfuel4.ui.lists

import androidx.compose.Composable

/**
 * Created by Roar on 21.11.2019.
 * Copyright RoGo Software / Gronmo IT
 */

@Composable
fun <T> ScrollingList(
    data: List<T>,
    body: @Composable() (T) -> Unit
){
    body(data[0])
}