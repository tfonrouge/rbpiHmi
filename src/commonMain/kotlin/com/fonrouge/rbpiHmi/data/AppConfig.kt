package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class AppConfig(
    val serialPortPath: String = "",
    val numericPassword: String = "0000",
    val baudRate: String = "115200",
)
