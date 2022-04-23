package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.views.AuthView
import com.fonrouge.rbpiHmi.views.ConfigView
import com.fonrouge.rbpiHmi.views.MainView
import com.fonrouge.rbpiHmi.views.SensorsView
import io.kvision.*
import io.kvision.html.*
import io.kvision.panel.root
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.toast.Toast
import io.kvision.toast.ToastMethod
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlin.math.max

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

var observableViewType = ObservableValue(App.ViewType.Main)

class App : Application() {

    private val headerTitlePrefix = "HMI v1.0"

    private var footerForm: FooterForm? = null

    init {
        require("css/kvapp.css")

        observableViewType.subscribe { viewType ->
            footerForm?.let { footerForm ->
                when (viewType) {
                    ViewType.Main -> {
                        footerForm.buttonMain.style = ButtonStyle.PRIMARY
                        footerForm.buttonSensors.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonConfig.style = ButtonStyle.OUTLINESECONDARY
                    }
                    ViewType.Sensors -> {
                        footerForm.buttonSensors.style = ButtonStyle.PRIMARY
                        footerForm.buttonMain.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonConfig.style = ButtonStyle.OUTLINESECONDARY
                    }
                    ViewType.Config -> {
                        footerForm.buttonConfig.style = ButtonStyle.PRIMARY
                        footerForm.buttonMain.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonSensors.style = ButtonStyle.OUTLINESECONDARY
                    }
                }
            }
        }
    }

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
//            div(className = "min-vh-100 d-flex flex-column justify-content-between") {
            div(className = "d-flex flex-column min-vh-100") {
                val header = header(content = headerTitlePrefix, className = "header1") {
                    align = Align.CENTER
                }
                main().bind(observableState = observableViewType) { viewType ->
                    when (viewType) {
                        ViewType.Main -> add(MainView())
                        ViewType.Sensors -> add(SensorsView())
                        ViewType.Config -> {
                            AppScope.launch {
                                val a = AuthView()
                                a.getResult()?.let {
                                    if (it) {
                                        add(ConfigView(footerForm))
                                    } else {
                                        observableViewType.setState(ViewType.Main)
                                        Toast.error(
                                            message = "Authentication failed: incorrect password.",
                                            options = ToastOptions(
                                                positionClass = ToastPosition.TOPRIGHT,
                                                hideMethod = ToastMethod.SLIDEUP
                                            )
                                        )
                                    }
                                } ?: kotlin.run {
                                    observableViewType.setState(ViewType.Main)
                                    Toast.warning(
                                        message = "Authentication cancelled.",
                                        options = ToastOptions(
                                            positionClass = ToastPosition.TOPRIGHT,
                                            hideMethod = ToastMethod.SLIDEUP
                                        )
                                    )
                                }
                            }
                        }
                    }
                    header.content = "$headerTitlePrefix - ${viewType.name}"
                }
                footer(className = "mt-auto") {
                    FooterForm().let {
                        footerForm = it
                        add(it)
                    }
                }
            }
        }
        AppScope.launch {
            val pingResult = Model.ping("Hello world from client!")
            Toast.info("$pingResult ${getViewport()}")
        }
    }

    private fun getViewport(): String {
        // https://stackoverflow.com/a/8876069
        val width = max(document.documentElement?.clientWidth ?: -1, window.innerWidth)
        if (width <= 576) return "xs"
        if (width <= 768) return "sm"
        if (width <= 992) return "md"
        if (width <= 1200) return "lg"
        return "xl"
    }

    enum class ViewType {
        Main,
        Sensors,
        Config,
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapSelectModule,
        BootstrapSpinnerModule,
        BootstrapIconsModule,
        FontAwesomeModule,
        ChartModule,
        TabulatorModule,
        ToastModule,
        PrintModule,
        CoreModule
    )
}
