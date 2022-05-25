package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.dataComm.HelloResponse

@kotlinx.serialization.Serializable
class AppConfig(
    val numericPassword: String = "0000",
    val pingTimeoutInterval: Int = 1000,
) {
    var helloResponse: HelloResponse? = null
    var serialCommConfig: SerialCommConfig = SerialCommConfig()
}
