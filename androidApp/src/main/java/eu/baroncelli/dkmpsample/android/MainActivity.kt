package eu.baroncelli.dkmpsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import eu.baroncelli.dkmpsample.android.styling.DkmpSampleTheme


class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coreModel = appViewModel.getCoreViewModel(applicationContext)
        setContent {
            DkmpSampleTheme {
                Navigation(coreModel)
            }
        }
    }

}