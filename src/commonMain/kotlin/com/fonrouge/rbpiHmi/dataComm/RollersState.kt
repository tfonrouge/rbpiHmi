package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.dataComm.enums.RollerId
import com.fonrouge.rbpiHmi.dataComm.enums.RollerState

@kotlinx.serialization.Serializable
class RollersState(
    val attachedRollerId: RollerId,
    val attachedRollerState: RollerState,
    val detachedRollerState: RollerState,
) {
    val detachedRollerId: RollerId
        get() = when (attachedRollerId) {
            RollerId.A -> RollerId.B
            RollerId.B -> RollerId.A
            RollerId.Unknown -> RollerId.Unknown
        }
}
