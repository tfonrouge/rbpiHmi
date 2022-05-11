package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.ContainerPLCState
import com.fonrouge.rbpiHmi.services.HmiService

object ModelHmi {
    private val hmiService = HmiService()

    suspend fun getHmiServiceState(): ContainerPLCState {
        return hmiService.getState()
    }
}
