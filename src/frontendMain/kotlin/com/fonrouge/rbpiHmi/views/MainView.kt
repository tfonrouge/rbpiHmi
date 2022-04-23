package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.Model
import com.fonrouge.rbpiHmi.data.RollerFeedState
import com.fonrouge.rbpiHmi.enums.RollerFeedPosition
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
import io.kvision.utils.rem
import kotlinx.browser.window
import kotlinx.coroutines.launch

class MainView : SimplePanel() {

    private lateinit var radialGaugeRollerFeed: React<Number>
    private lateinit var radialGaugeRollerA: React<Number>
    private lateinit var radialGaugeRollerB: React<Number>
    private lateinit var radialGaugeMotorA: React<Number>
    private lateinit var radialGaugeMotorB: React<Number>
    private lateinit var rollerStateAttachedImage: Image
    private lateinit var rollerStateAttachedLabel: Label
    private lateinit var rollerStateDetachedImage: Image
    private lateinit var rollerStateDetachedLabel: Label
    private lateinit var rollerFeedPositionImage: Image
    private lateinit var rollerFeedPositionLabel: Label

    init {
        flexPanel(
            direction = FlexDirection.ROW,
            wrap = FlexWrap.WRAP,
            justify = JustifyContent.FLEXSTART,
            alignItems = AlignItems.START,
            spacing = 10
        ) {
            flexPanel(
                direction = FlexDirection.COLUMN,
//                wrap = FlexWrap.WRAP,
//                justify = JustifyContent.FLEXEND,
                alignItems = AlignItems.STRETCH,
//                alignContent = AlignContent.SPACEAROUND
            ) {
                flexPanel(
                    direction = FlexDirection.COLUMN,
                    wrap = FlexWrap.WRAP,
                    justify = JustifyContent.SPACEEVENLY,
                    alignItems = AlignItems.STRETCH,
                    className = "flexPanelCtrl1"
                ) {
                    div(content = "Main Roller", rich = true, className = "title1")
                    radialGaugeRollerFeed = react {
                        RadialGauge {
                            setCanvasGaugesParams(RadialGaugeType.MainRoller, "A")
                        }
                    }
                }
                flexPanel(
                    direction = FlexDirection.COLUMN,
                    className = "flexPanelCtrl1",
                    alignItems = AlignItems.STRETCH
                ) {
                    div(content = "Feed Roller Status", rich = true, className = "title1")
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
                                width = 2.rem
                                height = 2.rem
                            }
                            rollerStateAttachedLabel = label(rich = true)
//                            rollerStateAttachedLabel = div(content = "?", rich = true)
                        }
                        image("double-arrow.png") {
                            width = 4.rem
                            height = 2.rem
                        }
                        flexPanel(direction = FlexDirection.COLUMN, alignItems = AlignItems.CENTER) {
                            rollerStateDetachedImage = image("question-mark-1.png") {
                                width = 2.rem
                                height = 2.rem
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
                        rollerFeedPositionImage = image(src = "question-mark-1.png") {
                            width = 2.rem
                            height = 2.rem
                        }
                        rollerFeedPositionLabel = label(rich = true)
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
                        div(content = "Feed Roller A", rich = true, className = "title1")
                        radialGaugeRollerA = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.FeedRoller, "A")
                            }
                        }
                    }
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "Feed Roller B", rich = true, className = "title1")
                        radialGaugeRollerB = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.FeedRoller, "B")
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
                        radialGaugeMotorA = react {
                            RadialGauge {
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
                        radialGaugeMotorB = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.Motor, "B")
                            }
                        }
                    }
                }
            }
        }

        window.setInterval({
            AppScope.launch {
                Model.hmiServiceGetState().let { hmiState ->
                    setRollerFeedState(hmiState.rollerFeedState)
                    setRollerFeedPosition(hmiState.rollerFeedPosition)
                }
            }
        }, timeout = 500)

    }

    private fun ReactCanvasGaugesRadialGaugeProps.setCanvasGaugesParams(
        radialGaugeType: RadialGaugeType, id: String? = null
    ) {

        val w = 300
        val h = 300

        when (radialGaugeType) {

            RadialGaugeType.MainRoller -> {
                colorBorderInner = "blue"
                width = w
                height = h
                units = "RPM"
                title = "Main Roller"
                value = 0
                minValue = 0
                maxValue = 250
                exactTicks = true
                majorTicks = arrayOf(50, 100, 150, 200, 250)
                minorTicks = 10
                highlights = "0"
            }

            RadialGaugeType.FeedRoller -> {
                colorBorderInner = "red"
                width = w
                height = h
                units = "RPM"
                title = "$id Roller"
                value = 0
                minValue = 0
                maxValue = 600
                exactTicks = true
                majorTicks = arrayOf(50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600)
                minorTicks = 10
                highlights = "0"

            }

            RadialGaugeType.Motor -> {
                colorBorderInner = "darkgreen"
                width = w
                height = h
                units = "RPM"
                title = "$id Motor"
                value = 0
                minValue = 0
                maxValue = 1800
                exactTicks = true
                majorTicks = arrayOf(200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800)
                minorTicks = 100
                highlights = "0"
            }
        }
    }

    private suspend fun setRollerFeedState(rollerFeedState: RollerFeedState) {
        rollerStateAttachedImage.src = rollerFeedState.attachedRollState.imageSrc
        rollerStateAttachedLabel.content = rollerFeedState.attachedRollId.name
        rollerStateDetachedImage.src = rollerFeedState.detachedRollState.imageSrc
        rollerStateDetachedLabel.content = rollerFeedState.detachedRollId.name
    }

    private fun setRollerFeedPosition(rollerFeedPosition: RollerFeedPosition) {
        rollerFeedPositionImage.src = rollerFeedPosition.imageSrc
        rollerFeedPositionLabel.content = rollerFeedPosition.name
    }

    enum class RadialGaugeType {
        MainRoller, FeedRoller, Motor
    }
}
