package com.fonrouge.rbpiHmi.tables

@kotlinx.serialization.Serializable
class Sensors(
    var id: Int,
    var pin: Int,
    var name: String,
)
