package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType

interface IResponse {
    val commId: Long
    val type: ResponseType
}
