package com.fonrouge.rbpiHmi.tables

@kotlinx.serialization.Serializable
class User(
    var id: String,
    var isAdmin: Boolean,
    var name: String,
)
