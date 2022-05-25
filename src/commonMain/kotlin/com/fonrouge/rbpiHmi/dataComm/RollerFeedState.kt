package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.dataComm.enums.RollerId
import com.fonrouge.rbpiHmi.dataComm.enums.RollerState

@kotlinx.serialization.Serializable
class RollerFeedState(
    val attachedRollerId: RollerId,
    val attachedRollerState: RollerState,
    val detachedRollerState: RollerState,
) {
    val detachedRollerId get() = if (attachedRollerId == RollerId.A) RollerId.B else RollerId.A
}
