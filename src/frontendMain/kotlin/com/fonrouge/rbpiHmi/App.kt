package com.fonrouge.rbpiHmi

import io.kvision.*
import io.kvision.html.Align
import io.kvision.html.footer
import io.kvision.html.header
import io.kvision.panel.flexPanel
import io.kvision.panel.root
import io.kvision.toast.Toast
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlin.math.max

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    init {
        require("css/kvapp.css")
    }

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
            header(content = "<h1>HMI v1.0</h1>", rich = true, className = "header1") {
                align = Align.CENTER
            }
            add(SensorsForm())
//            add(LoginForm())
            footer {
                add(FooterForm())
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
