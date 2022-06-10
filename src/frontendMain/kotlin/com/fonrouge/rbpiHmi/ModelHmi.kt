package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.dataComm.StateResponse
import com.fonrouge.rbpiHmi.services.HmiService

object ModelHmi {
    private val hmiService = HmiService()

    suspend fun getHmiServiceState(): StateResponse {
        return hmiService.getHmiServiceState()
    }
}
