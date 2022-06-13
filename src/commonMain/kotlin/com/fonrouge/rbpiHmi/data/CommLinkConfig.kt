package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.dataComm.HelloResponse

@kotlinx.serialization.Serializable
class CommLinkConfig(
    val numericPassword: String = "0000",
    val hmiRefreshInterval: Int = 1000,
) {
    var helloResponse: HelloResponse? = null
    var serialCommConfig: SerialCommConfig = SerialCommConfig()
}
