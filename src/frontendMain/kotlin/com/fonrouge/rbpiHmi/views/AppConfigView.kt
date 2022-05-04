package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.App
import com.fonrouge.rbpiHmi.AppConfigModel
import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.observableViewType
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.IConfigService
import io.kvision.core.FlexDirection
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.simpleSelectRemote
import io.kvision.form.text.password
import io.kvision.html.button
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import kotlinx.coroutines.launch

class AppConfigView : SimplePanel() {
//class AppConfigView : Dialog<Boolean>(
//    caption = "App Config:",
//    closeButton = true,
//    centered = true,
//    size = ModalSize.XLARGE,
//) {

    private lateinit var formPanel: FormPanel<AppConfig>

    init {
        formPanel = formPanel {
            flexPanel(direction = FlexDirection.COLUMN) {
                flexPanel(direction = FlexDirection.ROW, spacing = 10) {
                    simpleSelectRemote(
                        label = "Serial Port:",
                        serviceManager = ConfigServiceManager,
                        function = IConfigService::getSerialPortPathList,
                    ).bind(
                        key = AppConfig::serialPortPath,
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
                        key = AppConfig::baudRate,
                        required = true,
                        validatorMessage = { "Select baud rate for PLC comms" }
                    )
                    digitPad(type = DigitPad.Type.Password, label = "Admin password:").apply {
                        textWidget
                            .bind(
                                key = AppConfig::numericPassword,
                                required = true,
                                validatorMessage = { "only digits and length > 3" }
                            ) {
                                it.value?.let { s ->
                                    s.contains(regex = Regex("^\\d+$")) && s.length > 3
                                } ?: false
                            }
                    }
                }
                button(text = "Save Settings").onClick {
                    if (formPanel.validate()) {
                        AppScope.launch {
                            val d = formPanel.getData()
                            AppConfigModel.writeAppConfig(d)
                            observableViewType.value = App.ViewType.Main
//                            setResult()
                        }
                    }
                }
            }
        }
        AppScope.launch {
            formPanel.setData(AppConfigModel.appConfig())
        }
    }
}
