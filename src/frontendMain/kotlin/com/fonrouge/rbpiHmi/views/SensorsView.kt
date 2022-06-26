package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.*
import com.fonrouge.rbpiHmi.data.SensorsConfig
import io.kvision.form.formPanel
import io.kvision.panel.SimplePanel
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import kotlinx.browser.window
import kotlinx.coroutines.launch

class SensorsView : SimplePanel() {

    private var pingTimeoutInterval: Int? = null
        set(value) {
            if (field != value) {
                field = value
                startPeriodicUpdate(field)
            }
        }

    private fun startPeriodicUpdate(timeout: Int?) {
        intervalPingHandler = timeout?.let { it ->
            window.setInterval(
                handler = {
                    AppScope.launch {
                        if (ModelHello.helloResponseObservableValue.value == null) {
                            ModelHello.getHelloResponse()
                        } else {
                            getHmiServiceState()
                        }
                    }
                },
                timeout = it
            )
        }
    }

    private suspend fun getHmiServiceState() {
        try {
            ModelHmi.getHmiServiceState().apply {
            }
        } catch (e: Exception) {
            Toast.error(
                message = e.message ?: "?",
                title = "getHmiServiceState() error:",
                options = ToastOptions(
                    preventDuplicates = true,
                )
            )
        }
    }

    init {

        formPanel<SensorsConfig> { }

        AppScope.launch {
            pingTimeoutInterval = ModelAppConfig.hmiRefreshInterval()
            startPeriodicUpdate(pingTimeoutInterval)
        }
    }
}
