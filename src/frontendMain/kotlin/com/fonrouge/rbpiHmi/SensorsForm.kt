package com.fonrouge.rbpiHmi

import io.kvision.core.AlignItems
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import io.kvision.react.React
import io.kvision.react.react

class SensorsForm : SimplePanel() {

    private lateinit var radialGaugeRollerFeed: React<Number>
    private lateinit var radialGaugeRollerA: React<Number>
    private lateinit var radialGaugeRollerB: React<Number>
    private lateinit var radialGaugeMotorA: React<Number>
    private lateinit var radialGaugeMotorB: React<Number>

    init {
        flexPanel(
            FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER
        ) {
            flexPanel(
                FlexDirection.COLUMN, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER
            ) {
                flexPanel(
                    FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER,
                    spacing = 5
                ) {
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
//                alignItems = AlignItems.,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "<H4>Feed Roller A</H4>", rich = true, className = "title1")
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
//                alignItems = AlignItems.,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "<H4>Feed Roller B</H4>", rich = true, className = "title1")
                        radialGaugeRollerB = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.FeedRoller, "B")
                            }
                        }
                    }
                }

                flexPanel(
                    FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.FLEXSTART, AlignItems.CENTER,
                    spacing = 5
                ) {
                    flexPanel(
                        direction = FlexDirection.COLUMN,
                        wrap = FlexWrap.WRAP,
                        justify = JustifyContent.SPACEBETWEEN,
//                alignItems = AlignItems.,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "<H4>Motor A</H4>", rich = true, className = "title1")
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
//                alignItems = AlignItems.,
                        className = "flexPanelCtrl1"
                    ) {
                        div(content = "<H4>Motor B</H4>", rich = true, className = "title1")
                        radialGaugeMotorB = react {
                            RadialGauge {
                                setCanvasGaugesParams(RadialGaugeType.Motor, "B")
                            }
                        }
                    }
                }
            }
            flexPanel(
                direction = FlexDirection.COLUMN,
                wrap = FlexWrap.WRAP,
                justify = JustifyContent.SPACEEVENLY,
                alignItems = AlignItems.STRETCH,
                className = "flexPanelCtrl1"
            ) {
                div(content = "<H4>Main Roller</H4>", rich = true, className = "title1")
                radialGaugeRollerFeed = react {
                    RadialGauge {
                        setCanvasGaugesParams(RadialGaugeType.MainRoller, "A")
                    }
                }
            }
        }
    }

    enum class RadialGaugeType {
        MainRoller,
        FeedRoller,
        Motor
    }
}

private fun ReactCanvasGaugesProps.setCanvasGaugesParams(
    radialGaugeType: SensorsForm.RadialGaugeType,
    id: String? = null
) {

    val w = 250
    val h = 250

    console.warn("cssSize", w, h)

    when (radialGaugeType) {
        SensorsForm.RadialGaugeType.MainRoller -> {
            width = w
            height = h
            units = "RPM"
            title = "Main Roller"
            value = 0
            minValue = 0
            maxValue = 300
            exactTicks = true
            majorTicks = arrayOf(50, 100, 150, 200, 250, 300)
            minorTicks = 10
            highlights = "0"
        }
        SensorsForm.RadialGaugeType.FeedRoller -> {
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
        SensorsForm.RadialGaugeType.Motor -> {
            width = w
            height = h
            units = "RPM"
            title = "$id Motor"
            value = 0
            minValue = 0
            maxValue = 1800
            exactTicks = true
            majorTicks = arrayOf(
                200,
                400,
                600,
                800,
                1000,
                1200,
                1400,
                1600,
                1800
            )
            minorTicks = 100
            highlights = "0"
        }
    }
}
