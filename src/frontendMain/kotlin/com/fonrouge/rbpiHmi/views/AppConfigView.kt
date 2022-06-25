package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.*
import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.data.CommLinkConfig
import com.fonrouge.rbpiHmi.data.SensorsConfig
import com.fonrouge.rbpiHmi.data.SerialCommConfig
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.IConfigService
import io.kvision.core.*
import io.kvision.form.*
import io.kvision.form.check.checkBox
import io.kvision.form.select.simpleSelectRemote
import io.kvision.form.spinner.ButtonsType
import io.kvision.form.spinner.ForceType
import io.kvision.form.spinner.spinner
import io.kvision.html.Button
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.modal.Dialog
import io.kvision.panel.*
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

    private val advancedControlList = mutableListOf<GenericFormControl<*>>()
    private val advancedConfigDisabled = true

    init {
        tabPanel {
            tab(label = "Sensors") {
                flexPanel(direction = FlexDirection.COLUMN) {
                    marginTop = 1.rem
                    sensorsConfigFormPanel =
                        formPanel(type = FormType.HORIZONTAL, horizRatio = FormHorizontalRatio.RATIO_9) {
                            fieldsetPanel(legend = "Spinning winding roller after detaching signal:") {
                                spinner(
                                    label = "Milliseconds delay to start spinning:",
                                    min = 0,
                                    max = 5000,
                                    step = 5,
                                ).bind(SensorsConfig::delayToSpinWindingRoller, required = true)
                                spinner(
                                    label = "Detaching signal input Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::startDetachingSignalPinNumber, required = true)
                                spinner(
                                    label = "High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::startDetachingSignalHighState, required = true)
                            }
                            fieldsetPanel(legend = "Additional spin speed to winding roller:") {
                                spinner(
                                    label = "Percent:",
                                    min = -50,
                                    max = 50,
                                    decimals = 1
                                ).bind(SensorsConfig::additionalSpeedSpinToWindingRoller, required = true)
                            }
                            fieldsetPanel(legend = "Stop winded roller after cut operation:") {
                                spinner(label = "Milliseconds delay to stop winded roller:", min = 0, max = 60000)
                                    .bind(SensorsConfig::delayToStopWindedRollerAfterCut, required = true)
                                spinner(
                                    label = "Cut operation signal input Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::cutOperationSignalPinNumber, required = true)
                                spinner(
                                    label = "Cut operation signal input High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::cutOperationSignalHighState, required = true)
                            }
                            fieldsetPanel(legend = "Spin-up un-winded roller before attaching:") {
                                checkBox()
                                    .bind(SensorsConfig::spinUpUnWindedRollerBeforeAttaching, required = true)
                            }
                            fieldsetPanel(legend = "Main feeder roller rpm input signal:") {
                                spinner(
                                    label = "Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::feederRollerRpmSignalPinNumber, required = true)
                                spinner(
                                    label = "High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::feederRollerRpmSignalHighState, required = true)
                            }
                            fieldsetPanel(legend = "Winding roller rpm input signal:") {
                                spinner(
                                    label = "(A) winding roller Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::aWindingRollerRpmSignalPinNumber, required = true)
                                spinner(
                                    label = "(A) winding roller High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::aWindingRollerRpmSignalHighState, required = true)
                                spinner(
                                    label = "(B) winding roller Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::bWindingRollerRpmSignalPinNumber, required = true)
                                spinner(
                                    label = "(B) winding roller High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::bWindingRollerRpmSignalHighState, required = true)
                            }
                            fieldsetPanel(legend = "Start attaching input signal:") {
                                spinner(
                                    label = "Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::startAttachingSignalPinNumber, required = true)
                                spinner(
                                    label = "High State:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::startAttachingSignalHighState, required = true)
                            }
                            fieldsetPanel(legend = "Motor (A)") {
                                spinner(
                                    label = "Start/Stop Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::aMotorStartStopPinNumber, required = true)
                                spinner(
                                    label = "Speed Pin number:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::aMotorSpeedPinNumber, required = true)
                            }
                            fieldsetPanel(legend = "Motor (B)") {
                                spinner(
                                    label = "Start/Stop Pin number:",
                                    min = 0,
                                    max = 39
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::bMotorStartStopPinNumber, required = true)
                                spinner(
                                    label = "Speed Pin number:",
                                    min = 0,
                                    max = 1
                                ) {
                                    disabled = advancedConfigDisabled
                                    advancedControlList.add(this)
                                }.bind(SensorsConfig::bMotorSpeedPinNumber, required = true)
                            }
                            checkBox(label = "Edit advanced settings:") {
                                enableTooltip(
                                    options = TooltipOptions(
                                        title = "WARNING: enable only if you know what you are doing...",
                                        placement = Placement.TOP
                                    )
                                )
                            }.onEvent {
                                change = {
                                    advancedControlList.forEach {
                                        it.disabled = !self.value
                                    }
                                }
                            }
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
                        console.warn("WRITTING APPCONFIG")
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
