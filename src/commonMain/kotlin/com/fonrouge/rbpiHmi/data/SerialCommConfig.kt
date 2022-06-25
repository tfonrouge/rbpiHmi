package com.fonrouge.rbpiHmi.data

import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
class SerialCommConfig(
    @EncodeDefault
    val serialPortPath: String = "",
    @EncodeDefault
    val baudRate: String = "921600",
    @EncodeDefault
    val dataBits: Int = 8,
    @EncodeDefault
    val stopBits: Int = 1,
    @EncodeDefault
    val parity: Int = 0,
)
