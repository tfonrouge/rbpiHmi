package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.*
import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.data.CommLinkConfig
import com.fonrouge.rbpiHmi.data.SensorsConfig
import com.fonrouge.rbpiHmi.data.SerialCommConfig
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.IConfigService
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
import io.kvision.core.onEvent
import io.kvision.form.FormHorizontalRatio
import io.kvision.form.FormPanel
import io.kvision.form.FormType
import io.kvision.form.check.checkBox
import io.kvision.form.formPanel
import io.kvision.form.select.simpleSelectRemote
import io.kvision.form.spinner.ButtonsType
import io.kvision.form.spinner.ForceType
import io.kvision.form.spinner.spinner
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.modal.Dialog
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.panel.tab
import io.kvision.panel.tabPanel
import io.kvision.toast.Toast
import io.kvision.toast.ToastMethod
import io.kvision.toast.ToastOptions
import io.kvision.utils.event
import io.kvision.utils.rem
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppConfigView : FlexPanel(direction = FlexDirection.COLUMN) {

    private lateinit var serialCommConfigFormPanel: FormPanel<SerialCommConfig>
    private lateinit var sensorsConfigFormPanel: FormPanel<SensorsConfig>
    private lateinit var commLinkConfigFormPanel: FormPanel<CommLinkConfig>
    private var timeOutCancelled: Boolean? = null
    private var waitingResponse = false

    private val secsTimeout = 120
    private val countLimit = 30

    init {
        tabPanel {
            tab(label = "Sensors") {
                flexPanel(direction = FlexDirection.ROW) {
                    marginTop = 1.rem
                    sensorsConfigFormPanel =
                        formPanel(type = FormType.HORIZONTAL, horizRatio = FormHorizontalRatio.RATIO_9) {
                            spinner(
                                label = "MS to start spinning winding roller after detaching:",
                                min = 0,
                                max = 5000,
                                step = 5
                            )
                                .bind(SensorsConfig::delayToSpinWindingRoller, required = true)
                            spinner(
                                label = "% of additional spin speed to winding roller:",
                                min = -50,
                                max = 50,
                                decimals = 1
                            )
                                .bind(SensorsConfig::additionalSpeedSpinToWindingRoller, required = true)
                            spinner(label = "MS to stop winded roller after cut operation:", min = 0, max = 60000)
                                .bind(SensorsConfig::delayToStopWindedRollerAfterCut, required = true)
                            checkBox(label = "Spin un-winded roller before attaching:")
                                .bind(SensorsConfig::spinUnWindedRollerBeforeAttaching, required = true)
                        }
                }
            }
            tab("Comms") {
                flexPanel(direction = FlexDirection.ROW, wrap = FlexWrap.WRAP, justify = JustifyContent.SPACEEVENLY) {
                    marginTop = 1.rem
                    serialCommConfigFormPanel = formPanel {
                        flexPanel(
                            direction = FlexDirection.COLUMN,
                            justify = JustifyContent.SPACEEVENLY,
                            className = "flexPanelCtrl1"
                        ) {
                            flexPanel(
                                direction = FlexDirection.ROW,
                                spacing = 10,
                                justify = JustifyContent.SPACEEVENLY
                            ) {
                                simpleSelectRemote(
                                    label = "Serial Port:",
                                    serviceManager = ConfigServiceManager,
                                    function = IConfigService::getSerialPortPathList,
                                ).bind(
                                    key = SerialCommConfig::serialPortPath,
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
                                    key = SerialCommConfig::baudRate,
                                    required = true,
                                    validatorMessage = { "Select baud rate for PLC comms" }
                                )
                            }
                        }
                    }

                    commLinkConfigFormPanel = formPanel {
                        flexPanel(
                            direction = FlexDirection.COLUMN,
                            justify = JustifyContent.SPACEEVENLY,
                            className = "flexPanelCtrl1"
                        ) {
                            flexPanel(
                                direction = FlexDirection.ROW,
                                spacing = 10,
                                justify = JustifyContent.SPACEEVENLY
                            ) {
                                spinner(
                                    label = "Ping Interval millis",
                                    min = 100,
                                    max = 60000,
                                    step = 10,
                                    buttonsType = ButtonsType.VERTICAL,
                                    buttonStyle = ButtonStyle.OUTLINEPRIMARY,
                                    forceType = ForceType.ROUND
                                ).bind(
                                    key = CommLinkConfig::hmiRefreshInterval,
                                    required = true,
                                )
                                digitPad(type = DigitPad.Type.Password, label = "Admin password:")
                                    .apply {
                                        textWidget
                                            .bind(
                                                key = CommLinkConfig::numericPassword,
                                                required = true,
                                                validatorMessage = { "only digits and length > 3" }
                                            ) {
                                                it.value?.let { s ->
                                                    s.contains(regex = Regex("^\\d+$")) && s.length > 3
                                                } ?: false
                                            }
                                    }
                            }
                        }
                    }
                }
            }
        }

        flexPanel(
            direction = FlexDirection.ROW,
            justify = JustifyContent.FLEXEND
        ) {
            button(text = "Save Settings").onClick {
                if (serialCommConfigFormPanel.validate()) {
                    AppScope.launch {
                        val commLinkConfig = commLinkConfigFormPanel.getData()
                        commLinkConfig.serialCommConfig = serialCommConfigFormPanel.getData()
                        val sensorsConfig = sensorsConfigFormPanel.getData()
                        ModelAppConfig.writeAppConfig(
                            AppConfig(
                                commLinkConfig = commLinkConfig,
                                sensorsConfig = sensorsConfig
                            )
                        )
                        observableViewType.value = App.ViewType.Main
//                            setResult()
                    }
                }
            }
        }

        AppScope.launch {
            val appConfig = ModelAppConfig.appConfig()
            sensorsConfigFormPanel.setData(appConfig.sensorsConfig)
            commLinkConfigFormPanel.setData(appConfig.commLinkConfig)
            serialCommConfigFormPanel.setData(appConfig.commLinkConfig.serialCommConfig)
        }
        startTimeOut()
    }

    private fun startTimeOut() {
        timeoutAppConfigHandler = window.setTimeout(
            handler = {
                AppScope.launch {
                    confirmKeepDialog()
                }
            },
            timeout = secsTimeout * 1000
        )
    }

    private fun confirmKeepDialog() {
        val label1 = "Keep configuring ?"
        val labelFunc = { count: Int -> "$label1 ($count secs)" }
        val btn: Button
        val dialog: Dialog<Boolean> = Dialog(
            caption = "Confirm",
            centered = true
        )

        dialog.apply {
            btn = button(text = labelFunc(countLimit)).onClick {
                timeOutCancelled = true
                dialog.setResult(true)
            }
        }

        dialog.onEvent {
            event("show.bs.modal") {
                if (!waitingResponse) {
                    waitingResponse = true
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
