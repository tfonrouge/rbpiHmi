package com.fonrouge.rbpiHmi

import io.kvision.*
import io.kvision.core.AlignContent
import io.kvision.html.*
import io.kvision.panel.root
import io.kvision.toast.Toast
import io.kvision.utils.px
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    init {
        require("css/kvapp.css")
    }

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
            header(content = "<h1>HMI v1.0</h1>", rich = true) {
                align = Align.CENTER
            }
            add(LoginForm())
        }
        AppScope.launch {
            val pingResult = Model.ping("Hello world from client!")
            Toast.info(pingResult)
            console.warn(pingResult)
            root.add(Span(pingResult))
        }
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
