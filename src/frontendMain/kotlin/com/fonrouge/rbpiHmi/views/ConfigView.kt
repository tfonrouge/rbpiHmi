package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.FooterForm
import io.kvision.form.text.text
import io.kvision.panel.SimplePanel
import io.kvision.panel.vPanel

class ConfigView(footer: FooterForm?) : SimplePanel() {

    init {

        vPanel {
            text(label = "Hz")
        }
    }
}
