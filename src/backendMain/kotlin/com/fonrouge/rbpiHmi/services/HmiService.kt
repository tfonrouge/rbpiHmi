package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.SerialComm
import com.fonrouge.rbpiHmi.data.HmiState
import com.fonrouge.rbpiHmi.data.RollerFeedState
import com.fonrouge.rbpiHmi.enums.RollId
import com.fonrouge.rbpiHmi.enums.RollState
import com.fonrouge.rbpiHmi.enums.RollerFeedPosition

actual class HmiService : IHmiService {

    val serialComm = SerialComm

    override suspend fun getState(): HmiState {
        return HmiState(
            rollerFeedPosition = RollerFeedPosition.Feeding,
            rollerFeedState = RollerFeedState(
                attachedRollId = RollId.A,
                attachedRollState = RollState.Rotating,
                detachedRollState = RollState.Static,
            )
        )
    }
}
