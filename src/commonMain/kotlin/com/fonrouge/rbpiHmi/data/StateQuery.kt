package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class StateQuery(
    val commId: Long,
    val action: QueryAction
)
