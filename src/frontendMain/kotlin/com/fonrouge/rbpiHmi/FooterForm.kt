package com.fonrouge.rbpiHmi

import io.kvision.core.*
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel

class FooterForm : SimplePanel() {

    init {
        flexPanel(
            direction = FlexDirection.ROW,
            wrap = FlexWrap.WRAP,
            justify = JustifyContent.SPACEBETWEEN,
            alignItems = AlignItems.CENTER,
            alignContent = AlignContent.CENTER, spacing = 10,
            className = "footer1"
        ) {
            button("Main", style = ButtonStyle.OUTLINESECONDARY)
            button("Sensors", style = ButtonStyle.OUTLINESECONDARY)
            button("Config", style = ButtonStyle.OUTLINESECONDARY)
        }
    }
}
