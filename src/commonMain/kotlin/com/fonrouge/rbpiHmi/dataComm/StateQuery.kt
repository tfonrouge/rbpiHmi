package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.QueryAction

@kotlinx.serialization.Serializable
class StateQuery(
    val commId: Long,
    val action: QueryAction
)
