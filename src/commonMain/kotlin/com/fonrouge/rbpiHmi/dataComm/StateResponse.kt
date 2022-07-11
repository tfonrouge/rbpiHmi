package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.ResponseType
import com.fonrouge.rbpiHmi.dataComm.enums.TurretState

@kotlinx.serialization.Serializable
class StateResponse(
    override val commId: Long,
    override val type: ResponseType = ResponseType.State,
    val mainRollerRpm: Int,
    val aRollerRpm: Int,
    val bRollerRpm: Int,
    val aMotorStartupRpm: Double,
    val bMotorStartupRpm: Double,
    val aMotorFinalRpm: Double,
    val bMotorFinalRpm: Double,
    val turretState: TurretState,
    val rollersState: RollersState,
) : IResponse
