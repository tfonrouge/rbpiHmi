package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class AppConfig(
    val numericPassword: String = "0000",
    val pingTimeoutInterval: Int = 1000,
) {
    var serialCommConfig: SerialCommConfig = SerialCommConfig()
}
