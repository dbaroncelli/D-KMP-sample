package eu.baroncelli.dkmpsample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import eu.baroncelli.dkmpsample.android.styling.DKmpSampleTheme


class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DKmpSampleTheme(appViewModel.coreModel) {
                Navigation(appViewModel.coreModel)
            }
        }
    }

}