package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.enums.RollerFeedPosition

@kotlinx.serialization.Serializable
class HmiState(
    val valid: Boolean,
    val mainRollerRpm: Int,
    val aRollerRpm: Int,
    val bRollerRpm: Int,
    val aMotorRpm: Int,
    val bMotorRpm: Int,
    val rollerFeedPosition: RollerFeedPosition,
    val rollerFeedState: RollerFeedState,
)
