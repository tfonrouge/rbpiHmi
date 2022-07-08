package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

@kotlinx.serialization.Serializable
class MessageResponse(
    override val commId: Long,
    override val type: ResponseType = ResponseType.message,
    val msgType: String,
    val message: String,
) : IResponse
