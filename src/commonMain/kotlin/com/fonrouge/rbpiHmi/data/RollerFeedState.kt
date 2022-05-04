package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.enums.RollerId
import com.fonrouge.rbpiHmi.enums.RollerState

@kotlinx.serialization.Serializable
class RollerFeedState(
    val attachedRollerId: RollerId,
    val attachedRollerState: RollerState,
    val detachedRollerState: RollerState,
) {
    val detachedRollerId get() = if (attachedRollerId == RollerId.A) RollerId.B else RollerId.A
}
