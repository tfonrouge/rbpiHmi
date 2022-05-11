package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class ContainerPLCState(
    val valid: Boolean,
    val stateResponse: StateResponse?,
)
