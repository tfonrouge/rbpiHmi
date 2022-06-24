package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

@kotlinx.serialization.Serializable
class ConfigResponse(
    override val commId: Long,
    override val type: ResponseType = ResponseType.config
) : IResponse
