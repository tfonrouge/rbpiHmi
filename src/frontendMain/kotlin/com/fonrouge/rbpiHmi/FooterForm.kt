package com.fonrouge.rbpiHmi

import io.kvision.core.AlignItems
import io.kvision.core.FlexDirection
import io.kvision.core.FlexWrap
import io.kvision.core.JustifyContent
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
//            alignContent = AlignContent.CENTER,
            spacing = 10,
            className = "footer1"
        ) {
            button("Main", style = ButtonStyle.SECONDARY, className = "btn-lg")
            button("Sensors", style = ButtonStyle.SECONDARY)
            button("Config", style = ButtonStyle.SECONDARY)
        }
    }
}
