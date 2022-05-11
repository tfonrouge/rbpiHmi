package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

@kotlinx.serialization.Serializable
class HelloResponse(
    val commId: Long,
    val type: ResponseType = ResponseType.hello,
    val version: String,
)
