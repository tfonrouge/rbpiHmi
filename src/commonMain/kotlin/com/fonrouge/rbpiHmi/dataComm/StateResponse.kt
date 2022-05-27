package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType
import com.fonrouge.rbpiHmi.dataComm.enums.TurretState

@kotlinx.serialization.Serializable
class StateResponse(
    val commId: Long,
    val type: ResponseType = ResponseType.state,
    val mainRollerRpm: Int,
    val aRollerRpm: Int,
    val bRollerRpm: Int,
    val aMotorRpm: Int,
    val bMotorRpm: Int,
    val turretState: TurretState,
    val rollersState: RollersState,
)
