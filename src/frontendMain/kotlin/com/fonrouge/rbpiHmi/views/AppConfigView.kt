package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.App
import com.fonrouge.rbpiHmi.AppConfigModel
import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.observableViewType
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.IConfigService
import io.kvision.core.FlexDirection
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.simpleSelectRemote
import io.kvision.html.Button
import io.kvision.html.button
import io.kvision.modal.Dialog
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.toast.Toast
import io.kvision.toast.ToastMethod
import io.kvision.toast.ToastOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.js.timers.setTimeout

class AppConfigView : FlexPanel(direction = FlexDirection.COLUMN) {

    private lateinit var formPanel: FormPanel<AppConfig>
    private var timeOutCancelled: Boolean? = null
    private var waitingResponse = false

    val secsTimeout = 120
    val countLimit = 30

    init {
        formPanel = formPanel {
            flexPanel(direction = FlexDirection.COLUMN) {
                flexPanel(direction = FlexDirection.ROW, spacing = 10) {
                    simpleSelectRemote(
                        label = "Serial Port:",
                        serviceManager = ConfigServiceManager,
                        function = IConfigService::getSerialPortPathList,
                    ).bind(
                        key = AppConfig::serialPortPath,
                        required = true,
                        validatorMessage = { "Serial port to PLC" },
                    ) {
                        it.value?.isNotEmpty()
                    }
                    simpleSelectRemote(
                        label = "Baud rate:",
                        serviceManager = ConfigServiceManager,
                        function = IConfigService::getBaudRateList,
                    ).bind(
                        key = AppConfig::baudRate,
                        required = true,
                        validatorMessage = { "Select baud rate for PLC comms" }
                    )
                    digitPad(type = DigitPad.Type.Password, label = "Admin password:").apply {
                        textWidget
                            .bind(
                                key = AppConfig::numericPassword,
                                required = true,
                                validatorMessage = { "only digits and length > 3" }
                            ) {
                                it.value?.let { s ->
                                    s.contains(regex = Regex("^\\d+$")) && s.length > 3
                                } ?: false
                            }
                    }
                }
                button(text = "Save Settings").onClick {
                    if (formPanel.validate()) {
                        AppScope.launch {
                            val d = formPanel.getData()
                            AppConfigModel.writeAppConfig(d)
                            observableViewType.value = App.ViewType.Main
//                            setResult()
                        }
                    }
                }
            }
        }
        AppScope.launch {
            formPanel.setData(AppConfigModel.appConfig())
        }
        startTimeOut()
    }

    private fun startTimeOut() {
        setTimeout(
            callback = {
                AppScope.launch {
                    confirmKeepDialog()
                }
            },
            ms = secsTimeout * 1000
        )
    }

    private fun confirmKeepDialog() {
        val label1 = "Keep configuring ?"
        val labelFunc = { count: Int -> "$label1 ($count secs)" }
        val btn: Button
        val dialog: Dialog<Boolean> = Dialog(caption = "Confirm")

        dialog.apply {
            btn = button(text = labelFunc(countLimit)).onClick {
                timeOutCancelled = true
                dialog.setResult(true)
            }
        }

        dialog.onEvent {
            focus = {
                if (!waitingResponse) {
                    waitingResponse = true
                    console.warn("focus", it)
                    AppScope.launch {
                        var countdown = countLimit
                        timeOutCancelled = false
                        while (countdown > 0 && timeOutCancelled == false) {
                            delay(1000)
                            --countdown
                            console.warn(countdown)
                            btn.text = labelFunc(countdown)
                        }
                        if (timeOutCancelled == false) {
                            dialog.setResult(false)
                        }
                    }
                }
            }
        }

        AppScope.launch {
            if (dialog.getResult() != true) {
                Toast.warning(
                    message = "Closing Configuration by timeout",
                    options = ToastOptions(
//                        positionClass = ToastPosition.TOPLEFT,
                        hideMethod = ToastMethod.SLIDEUP
                    )
                )
                observableViewType.value = App.ViewType.Main
            } else {
                waitingResponse = false
                startTimeOut()
            }
            timeOutCancelled = null
        }
    }
}
