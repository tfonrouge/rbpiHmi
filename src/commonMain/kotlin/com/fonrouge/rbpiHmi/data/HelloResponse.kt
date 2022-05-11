package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class HelloResponse(
    val commId: Long,
    val type: ResponseType = ResponseType.hello,
    val version: String,
)
