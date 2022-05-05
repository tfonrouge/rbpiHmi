package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class AppConfig(
    val serialPortPath: String,
    val numericPassword: String,
    val baudRate: String,
    val pingTimeoutInterval: Int,
)
