package com.fonrouge.rbpiHmi

import csstype.attr
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import io.kvision.react.react
import io.kvision.utils.px

class SensorsForm : SimplePanel() {

    init {
        flexPanel(
            FlexDirection.ROW, FlexWrap.WRAP, JustifyContent.SPACEEVENLY, AlignItems.CENTER,
            spacing = 5
        ) {
            flexPanel(
                direction = FlexDirection.COLUMN,
                wrap = FlexWrap.WRAP,
                justify = JustifyContent.SPACEBETWEEN,
//                alignItems = AlignItems.,
                className = "flexPanelCtrl1"
            ) {
                div(content = "<H3>Main Feed</H3> Roller Status", rich = true, className = "title1")
                button(text = "ON")
                span(content = "1200 rpm")
                react {
                    ReactCanvasGauges {
                        units = "RPM"
                        title = "Main Feed Roller"
                        minValue = 0
                        maxValue = 500
                    }
//                    RadialGauge()
                }
            }
            flexPanel(
                direction = FlexDirection.COLUMN,
                wrap = FlexWrap.WRAP,
                justify = JustifyContent.SPACEBETWEEN,
//                alignItems = AlignItems.,
                className = "flexPanelCtrl1"
            ) {
                div(content = "<H3>A</H3> Roller Status", rich = true, className = "title1")
                button(text = "ON")
                span(content = "1200 rpm")
                react {
                    ReactCanvasGauges {
//                        createElement = "RadialGauge"
                    }
//                    RadialGauge()
                }
            }
            flexPanel(
                direction = FlexDirection.COLUMN,
                wrap = FlexWrap.WRAP,
                justify = JustifyContent.SPACEBETWEEN,
//                alignItems = AlignItems.,
                className = "flexPanelCtrl1"
            ) {
                div(content = "<H3>B</H3> Roller Status", rich = true, className = "title1")
                button(text = "ON")
                span(content = "1200 rpm")
                react {
                    ReactCanvasGauges {
//                        createElement = "RadialGauge"
                    }
//                    RadialGauge()
                }
            }
        }
    }

    private fun Container.customDiv(value: String, size: Int): Tag {
        return div(value).apply {
            paddingTop = ((size / 2) - 10).px
            align = Align.CENTER
            background = Background(Color.name(Col.GREEN))
            width = size.px
            height = size.px
        }
    }
}
