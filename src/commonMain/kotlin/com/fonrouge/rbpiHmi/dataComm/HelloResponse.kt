package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

@kotlinx.serialization.Serializable
class HelloResponse(
    val commId: Long,
    val type: ResponseType = ResponseType.hello,
    val item: String,
    val maker: String,
    val www: String,
    val email: String,
    val buildDate: String,
    val version: String,
    val user: String,
)
