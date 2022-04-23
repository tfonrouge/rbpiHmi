package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.FooterForm
import com.fonrouge.rbpiHmi.data.ConfigState
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.IConfigService
import io.kvision.form.formPanel
import io.kvision.form.select.selectRemote
import io.kvision.form.select.simpleSelectRemote
import io.kvision.form.text.text
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import io.kvision.panel.vPanel

class ConfigView(footer: FooterForm?) : SimplePanel() {

    init {
        formPanel<ConfigState> {
            flexPanel {
                simpleSelectRemote(
                    label = "Serial Port:",
                    serviceManager = ConfigServiceManager,
                    function = IConfigService::getSerialPortUrlList,
                )
            }
        }
    }
}
