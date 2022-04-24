package com.fonrouge.rbpiHmi.views

import io.kvision.form.text.text
import io.kvision.panel.SimplePanel
import io.kvision.panel.vPanel

class SensorsView : SimplePanel() {

    init {

        vPanel {
            text(label = "Sensor")
        }

    }
}
