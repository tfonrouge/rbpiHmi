package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.views.AppConfigView
import com.fonrouge.rbpiHmi.views.AuthView
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

var observableViewType: ObservableValue<App.ViewType?> = ObservableValue(null)

var intervalPingHandler: Int? = null
    set(value) {
        field?.let { window.clearInterval(it) }
        field = value
    }

var timeoutAppConfigHandler: Int? = null
    set(value) {
        field?.let { window.clearTimeout(it) }
        field = value
    }

class App : Application() {

    private val headerTitlePrefix = "HMI v1.1"

    private var footerForm: FooterForm? = null

    init {

        require("css/kvapp.css")

        observableViewType.subscribe { viewType ->
            if (viewType != ViewType.Main) {
                intervalPingHandler = null
            }
            if (viewType != ViewType.ConfigAuth) {
                timeoutAppConfigHandler = null
            }
            footerForm?.let { footerForm ->
                when (viewType) {
                    ViewType.Main -> {
                        footerForm.buttonMain.disabled = true
                        footerForm.buttonSensors.disabled = false
                        footerForm.buttonConfig.disabled = false
                        footerForm.buttonMain.style = ButtonStyle.PRIMARY
                        footerForm.buttonSensors.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonConfig.style = ButtonStyle.OUTLINESECONDARY
                    }
                    ViewType.Sensors -> {
                        footerForm.buttonMain.disabled = false
                        footerForm.buttonSensors.disabled = true
                        footerForm.buttonConfig.disabled = false
                        footerForm.buttonSensors.style = ButtonStyle.PRIMARY
                        footerForm.buttonMain.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonConfig.style = ButtonStyle.OUTLINESECONDARY
                    }
                    ViewType.Config, ViewType.ConfigAuth -> {
                        footerForm.buttonMain.disabled = false
                        footerForm.buttonSensors.disabled = false
                        footerForm.buttonConfig.disabled = true
                        footerForm.buttonConfig.style = ButtonStyle.PRIMARY
                        footerForm.buttonMain.style = ButtonStyle.OUTLINESECONDARY
                        footerForm.buttonSensors.style = ButtonStyle.OUTLINESECONDARY
                    }
                    null -> {

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
                        ViewType.Config -> add(AuthView())
                        ViewType.ConfigAuth -> add(AppConfigView())
                        null -> {

                        }
                    }
                    header.content = "$headerTitlePrefix - ${viewType?.name}"
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
            observableViewType.value = ViewType.Main
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
        ConfigAuth,
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
