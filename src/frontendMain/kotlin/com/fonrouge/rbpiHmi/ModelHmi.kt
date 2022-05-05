package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.HmiState
import com.fonrouge.rbpiHmi.services.HmiService

object ModelHmi {
    private val hmiService = HmiService()

    suspend fun hmiServiceGetState(): HmiState {
        return hmiService.getState()
    }
}
