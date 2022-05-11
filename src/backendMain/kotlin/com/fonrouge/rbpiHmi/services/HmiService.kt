package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.ContainerPLCState
import com.fonrouge.rbpiHmi.data.PLCComm

actual class HmiService : IHmiService {

    override suspend fun getState(): ContainerPLCState {
        val stateResponse = PLCComm.sendStateQuery()
        return ContainerPLCState(
            valid = stateResponse != null,
            stateResponse = stateResponse
        )
    }
}
