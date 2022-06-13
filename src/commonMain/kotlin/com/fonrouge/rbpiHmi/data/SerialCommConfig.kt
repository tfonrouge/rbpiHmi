package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class SerialCommConfig(
    val serialPortPath: String = "",
    val baudRate: String = "921600",
    val dataBits: Int = 8,
    val stopBits: Int = 1,
    val parity: Int = 0,
)
