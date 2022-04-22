package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.views.ConfigView
import com.fonrouge.rbpiHmi.views.MainView
import com.fonrouge.rbpiHmi.views.SensorsView
import io.kvision.*
import io.kvision.html.*
import io.kvision.panel.root
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.toast.Toast
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlin.math.max

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

var viewTypeObservableValue = ObservableValue(App.ViewType.Main)

class App : Application() {

    private val headerTitlePrefix = "HMI v1.0"

    init {
        require("css/kvapp.css")
    }

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
//            div(className = "min-vh-100 d-flex flex-column justify-content-between") {
            div(className = "d-flex flex-column min-vh-100") {
                val header = header(content = headerTitlePrefix, className = "header1") {
                    align = Align.CENTER
                }
                main().bind(observableState = viewTypeObservableValue) {
                    when (it) {
                        ViewType.Main -> add(MainView())
                        ViewType.Sensors -> add(SensorsView())
                        ViewType.Config -> add(ConfigView())
                    }
                    header.content = "$headerTitlePrefix - ${it.name}"
                }
                footer(className = "mt-auto") {
                    add(FooterForm())
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
