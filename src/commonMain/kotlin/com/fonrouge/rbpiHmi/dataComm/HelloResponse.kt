package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

@kotlinx.serialization.Serializable
class HelloResponse(
    override val commId: Long,
    override val type: ResponseType = ResponseType.Hello,
    val item: String,
    val maker: String,
    val www: String,
    val email: String,
    val buildDate: String,
    val version: String,
    val user: String,
) : IResponse
