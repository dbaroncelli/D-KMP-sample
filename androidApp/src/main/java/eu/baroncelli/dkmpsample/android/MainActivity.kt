package eu.baroncelli.dkmpsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.baroncelli.dkmpsample.composables.MainComposable
import eu.baroncelli.dkmpsample.composables.styling.MyMaterialTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = (application as DKMPApp).model
        setContent {
            MyMaterialTheme {
                MainComposable(model)
            }
        }
    }

}