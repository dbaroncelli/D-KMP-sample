package eu.baroncelli.dkmpsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.baroncelli.dkmpsample.android.styling.DkmpSampleTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = (application as MyApp).kmpViewModel
        setContent {
            DkmpSampleTheme {
                Navigation(model)
            }
        }
    }

}