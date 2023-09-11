package eu.baroncelli.dkmpsample.composables

import androidx.compose.runtime.Composable
import eu.baroncelli.dkmpsample.composables.navigation.Router
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel

@Composable
fun MainComposable(model: DKMPViewModel) {
    val dkmpNav = model.navigation
    dkmpNav.Router()
}

