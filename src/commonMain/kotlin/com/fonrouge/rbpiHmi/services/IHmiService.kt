package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.dataComm.StateResponse
import io.kvision.annotations.KVService

@KVService
interface IHmiService {
    suspend fun getHmiServiceState(): StateResponse
}
