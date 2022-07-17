package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.*
import com.fonrouge.rbpiHmi.dataComm.RollersState
import com.fonrouge.rbpiHmi.dataComm.enums.TurretState
import com.fonrouge.rbpiHmi.lib.RadialGauge
import com.fonrouge.rbpiHmi.lib.ReactCanvasGaugesRadialGaugeProps
import io.kvision.core.AlignItems
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import io.kvision.react.React
import io.kvision.react.react
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.kvision.utils.rem
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlin.js.json

class MainView : SimplePanel() {

    private lateinit var radialGaugeMainRollerRpm: React<Int>
    private lateinit var radialGaugeARollerRpm: React<Int>
    private lateinit var radialGaugeBRollerRpm: React<Int>
    private lateinit var radialGaugeAMotorRpm: React<Int>
    private lateinit var radialGaugeBMotorRpm: React<Int>
    private lateinit var rollerStateAttachedImage: Image
    private lateinit var rollerStateAttachedLabel: Label
    private lateinit var rollerStateDetachedImage: Image
    private lateinit var rollerStateDetachedLabel: Label
    private lateinit var rollerWindPositionImage: Image
    private lateinit var rollerWindPositionLabel: Label

    private var pingTimeoutInterval: Int? = null
        set(value) {
            if (field != value) {
                field = value
                startPeriodicUpdate(field)
            }
        }

    init {

        console.warn("onInit MainView....")

        flexPanel(
            direction = FlexDirection.ROW,
            wrap = FlexWrap.WRAP,
            justify = JustifyContent.FLEXSTART,
            alignItems = AlignItems.START,
            spacing = 10
        ) {
            flexPanel(
                direction = FlexDirection.COLUMN,
                alignItems = AlignItems.STRETCH,
            ) {
                flexPanel(
                    direction = FlexDirection.COLUMN,
                    wrap = FlexWrap.WRAP,
                    justify = JustifyContent.SPACEEVENLY,
                    alignItems = AlignItems.STRETCH,
                    className = "flexPanelCtrl1"
                ) {
                    div(content = "Main Roller", rich = true, className = "title1")
                    radialGaugeMainRollerRpm = react(250) { getState, _ ->
                        RadialGauge {
                            animation = true
                            animatedValue = true
                            animationDuration = 500
                            value = getState()
                            setCanvasGaugesParams(RadialGaugeType.MainRoller, "A")
                        }
                    }
                }
                flexPanel(
                    direction = FlexDirection.COLUMN,
                    className = "flexPanelCtrl1",
                    alignItems = AlignItems.STRETCH
                ) {
                    div(content = "Wind Roller Status", rich = true, className = "title1")
                    flexPanel(
                        direction = FlexDirection.ROW,
                        alignItems = AlignItems.BASELINE,
                        justify = JustifyContent.SPACEBETWEEN,
//                    alignContent = AlignContent.CENTER,
                        className = "flexPanelCtrl1",
                        spacing = 10
                    ) {
                        flexPanel(direction = FlexDirection.COLUMN, alignItems = AlignItems.CENTER) {
                            rollerStateAttachedImage = image("question-mark-1.png") {
                                width = 1.5.rem
                                height = 1.5.rem
                            }
                            rollerStateAttachedLabel = label(rich = true)
//                            rollerStateAttachedLabel = div(content = "?", rich = true)
                        }
                        image("double-arrow.png") {
                            width = 1.5.rem
                            height = 1.5.rem
                        }
                        flexPanel(direction = FlexDirection.COLUMN, alignItems = AlignItems.CENTER) {
                            rollerStateDetachedImage = image("question-mark-1.png") {
                                width = 1.5.rem
                                height = 1.5.rem
                            }
                            rollerStateDetachedLabel = label(rich = true)
                        }
                    }
                    flexPanel(
                        direction = FlexDirection.ROW,
                        alignItems = AlignItems.CENTER,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1",
                    ) {
                        rollerWindPositionImage = image(src = "question-mark-1.png") {
                            width = 1.5.rem
                            height = 1.5.rem
                        }
                        rollerWindPositionLabel = label(rich = true)
                    }
                }
            }
            flexPanel(
                FlexDirection.COLUMN, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER
            ) {
                flexPanel(
                    FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER, spacing = 5
                ) {
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "Wind Roller A", rich = true, className = "title1")
                        radialGaugeARollerRpm = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.WindRoller, "A")
                            }
                        }
                    }
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "Wind Roller B", rich = true, className = "title1")
                        radialGaugeBRollerRpm = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.WindRoller, "B")
                            }
                        }
                    }
                }

                flexPanel(
                    FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER, spacing = 5
                ) {
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "Motor A", rich = true, className = "title1")
                        radialGaugeAMotorRpm = react(0) { getState, _ ->
                            RadialGauge {
                                value = getState()
                                setCanvasGaugesParams(RadialGaugeType.Motor, "A")
                            }
                        }
                    }
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "Motor B", rich = true, className = "title1")
                        radialGaugeBMotorRpm = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.Motor, "B")
                            }
                        }
                    }
                }
            }
        }

        AppScope.launch {
            pingTimeoutInterval = ModelAppConfig.hmiRefreshInterval()
            startPeriodicUpdate(pingTimeoutInterval)
        }
    }

    private suspend fun getHmiServiceState() {
        try {
            ModelHmi.getHmiServiceState().apply {
                radialGaugeMainRollerRpm.state = mainRollerRpm
                radialGaugeARollerRpm.state = aRollerRpm
                radialGaugeBRollerRpm.state = bRollerRpm
                radialGaugeAMotorRpm.state = aMotorFinalRpm
                radialGaugeBMotorRpm.state = bMotorFinalRpm
                setRollerWindState(rollersState)
                setRollerWindPosition(turretState)
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

    private fun ReactCanvasGaugesRadialGaugeProps.setCanvasGaugesParams(
        radialGaugeType: RadialGaugeType, id: String? = null
    ) {

        val w = 150
        val h = 150

        when (radialGaugeType) {

            RadialGaugeType.MainRoller -> {
                colorBorderInner = "blue"
                width = w
                height = h
                units = "RPM"
                title = "Main Roller"
                minValue = 0
                maxValue = 200
                exactTicks = true
                majorTicks = arrayOf(50, 100, 150, 200, 200)
                minorTicks = 10
                highlights = arrayOf(
                    json("from" to 0, "to" to 90, "color" to "lightgray"),
                    json("from" to 90, "to" to 120, "color" to "darkgray"),
                    json("from" to 120, "to" to 150, "color" to "gray"),
                    json("from" to 150, "to" to 200, "color" to "red"),
                )
            }

            RadialGaugeType.WindRoller -> {
                colorBorderInner = "red"
                width = w
                height = h
                units = "RPM"
                title = "$id Roller"
                minValue = 0
                maxValue = 600
                exactTicks = true
                majorTicks = arrayOf(50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600)
                minorTicks = 10
                highlights = arrayOf(
                    json("from" to 0, "to" to 240, "color" to "lightgray"),
                    json("from" to 240, "to" to 400, "color" to "darkgray"),
                    json("from" to 400, "to" to 500, "color" to "gray"),
                    json("from" to 500, "to" to 600, "color" to "red"),
                )
            }

            RadialGaugeType.Motor -> {
                colorBorderInner = "darkgreen"
                width = w
                height = h
                units = "RPM"
                title = "$id Motor"
                minValue = 0
                maxValue = 2000
                exactTicks = true
                majorTicks = arrayOf(200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000)
                minorTicks = 100
                highlights = arrayOf(
                    json("from" to 0, "to" to 720, "color" to "lightgray"),
                    json("from" to 720, "to" to 1200, "color" to "darkgray"),
                    json("from" to 1200, "to" to 1500, "color" to "gray"),
                    json("from" to 1500, "to" to 2000, "color" to "red"),
                )
            }
        }
    }

    private fun setRollerWindState(rollersState: RollersState) {
        rollerStateAttachedImage.src = rollersState.attachedRollerState.imageSrc
        rollerStateAttachedLabel.content = rollersState.attachedRollerId.name
        rollerStateDetachedImage.src = rollersState.detachedRollerState.imageSrc
        rollerStateDetachedLabel.content = rollersState.detachedRollerId.name
    }

    private fun setRollerWindPosition(turretState: TurretState) {
        rollerWindPositionImage.src = turretState.imageSrc
        rollerWindPositionLabel.content = turretState.name
    }

    enum class RadialGaugeType {
        MainRoller, WindRoller, Motor
    }
}
