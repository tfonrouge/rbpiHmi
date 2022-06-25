package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
class CommLinkConfig(
    @EncodeDefault
    val numericPassword: String = "0000",
    @EncodeDefault
    val hmiRefreshInterval: Int = 1000,
) {
    var helloResponse: HelloResponse? = null
    var serialCommConfig: SerialCommConfig = SerialCommConfig()
}
