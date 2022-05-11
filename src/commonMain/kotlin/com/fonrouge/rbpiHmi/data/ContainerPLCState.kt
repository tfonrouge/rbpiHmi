package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.dataComm.StateResponse

@kotlinx.serialization.Serializable
class ContainerPLCState(
    val valid: Boolean,
    val stateResponse: StateResponse?,
)
