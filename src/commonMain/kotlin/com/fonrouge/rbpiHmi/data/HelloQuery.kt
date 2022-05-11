package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class HelloQuery(
    val commId: Long,
    val action: QueryAction,
)
