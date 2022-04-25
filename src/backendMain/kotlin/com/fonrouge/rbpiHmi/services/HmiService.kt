package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.HmiState
import com.fonrouge.rbpiHmi.data.RollerFeedState
import com.fonrouge.rbpiHmi.enums.RollId
import com.fonrouge.rbpiHmi.enums.RollState
import com.fonrouge.rbpiHmi.enums.RollerFeedPosition
import kotlin.random.Random

actual class HmiService : IHmiService {

    override suspend fun getState(): HmiState {
        val mainRollerRpm = Random.nextInt(200)
        println("random: $mainRollerRpm")
        return HmiState(
            valid = true,
            mainRollerRpm = mainRollerRpm,
            aRollerRpm = 100,
            bRollerRpm = 100,
            aMotorRpm = 100,
            bMotorRpm = 100,
            rollerFeedPosition = RollerFeedPosition.Feeding,
            rollerFeedState = RollerFeedState(
                attachedRollId = RollId.A,
                attachedRollState = RollState.Rotating,
                detachedRollState = RollState.Static,
            ),
        )
    }
}
