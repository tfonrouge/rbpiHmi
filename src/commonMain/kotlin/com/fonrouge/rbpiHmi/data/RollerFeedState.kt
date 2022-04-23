package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.enums.RollId
import com.fonrouge.rbpiHmi.enums.RollState

@kotlinx.serialization.Serializable
class RollerFeedState(
    val attachedRollId: RollId,
    val attachedRollState: RollState,
    val detachedRollState: RollState,
) {
    val detachedRollId get() = if (attachedRollId == RollId.A) RollId.B else RollId.A
}
