package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.dataComm.StateResponse
import io.kvision.remote.ServiceException

actual class HmiService : IHmiService {

    override suspend fun getHmiServiceState(): StateResponse {
        return PLCComm.sendStateQuery() ?: throw ServiceException("getHmiServiceState() error...")
    }
}
