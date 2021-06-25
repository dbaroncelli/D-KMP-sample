
import eu.baroncelli.dkmpsample.shared.viewmodel.DKMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.getWebInstance
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable



fun main() = application { model ->
    renderComposable(rootElementId = "root") {
        Text("Web App still not implemented")
    }
}



// this coroutine mechanism is required, because SqlDelight for Web
// (which is part of the DKMPViewModel) needs to be instantiated asynchronously
fun application(block: suspend (DKMPViewModel) -> Unit) {
    MainScope().launch {
        val model = DKMPViewModel.getWebInstance()
        block(model)
    }
}
