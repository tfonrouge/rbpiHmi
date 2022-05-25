package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType
import com.fonrouge.rbpiHmi.dataComm.enums.RollerFeedPosition

@kotlinx.serialization.Serializable
class StateResponse(
    val commId: Long,
    val type: ResponseType = ResponseType.state,
    val mainRollerRpm: Int,
    val aRollerRpm: Int,
    val bRollerRpm: Int,
    val aMotorRpm: Int,
    val bMotorRpm: Int,
    val rollerFeedPosition: RollerFeedPosition,
    val rollerFeedState: RollerFeedState,
)
