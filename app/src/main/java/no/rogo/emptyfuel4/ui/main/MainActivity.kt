package no.rogo.emptyfuel4.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import no.rogo.emptyfuel4.data.getImageBank
import no.rogo.emptyfuel4.data.imageBank
import no.rogo.emptyfuel4.model.LocationData
import no.rogo.emptyfuel4.ui.app.EmptyFuel4App

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageBank = getImageBank(
            imageBank,
            resources)

        var deviceLocation = LocationData(61.89, 6.67)

        setContent {
            EmptyFuel4App(deviceLocation)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun DefaultPreview() {

    //EmptyFuel4App()
}
