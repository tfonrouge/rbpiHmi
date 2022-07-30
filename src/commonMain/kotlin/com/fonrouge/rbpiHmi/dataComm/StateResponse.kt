package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType
import com.fonrouge.rbpiHmi.dataComm.enums.TurretState

@kotlinx.serialization.Serializable
class StateResponse(
    override val commId: Long,
    override val type: ResponseType = ResponseType.State,
    val mainRollerRpm: Int,
    val aMotorRpm: Int,
    val bMotorRpm: Int,
    val aMotorStartupRpm: Int,
    val bMotorStartupRpm: Int,
    val aMotorFinalRpm: Int,
    val bMotorFinalRpm: Int,
    val turretState: TurretState,
    val rollersState: RollersState,
) : IResponse
