package com.fonrouge.rbpiHmi.data

import kotlinx.datetime.LocalDateTime

@kotlinx.serialization.Serializable
data class PLCConfig(
    val id: Int,
    val dateCreated: LocalDateTime,
    val dateModified: LocalDateTime,
    val title: String,
    val body: String
)
