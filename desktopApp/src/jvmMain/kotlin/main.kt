import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.application
import eu.baroncelli.dkmpsample.composables.MainComposable
import eu.baroncelli.dkmpsample.composables.styling.MyMaterialTheme
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getDesktopInstance


fun main() = application {
    val model = DKMPViewModel.Factory.getDesktopInstance()
    Window(
        title = "D-KMP sample for Compose Desktop",
        size = IntSize(1050, 700),
    ) {
        MyMaterialTheme {
            MainComposable(model)
        }
    }
}
