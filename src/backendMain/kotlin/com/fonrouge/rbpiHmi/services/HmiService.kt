package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.AppConfigFactory
import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.dataComm.StateResponse
import com.fonrouge.rbpiHmi.services.HelloService.Companion.helloResponse
import io.kvision.remote.ServiceException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

actual class HmiService : IHmiService {

    companion object {
        var stateResponse: StateResponse? = null
    }

    override suspend fun getHmiServiceState(): StateResponse {
        if (helloResponse != null) {
            stateResponse = null
            PLCComm.sendStateQuery()
            val respMillis = AppConfigFactory.appConfig.commLinkConfig.hmiRefreshInterval + 1000L
            try {
                withTimeout(respMillis) {
                    while (stateResponse == null) {
                        delay(10)
                    }
                }
                stateResponse?.let {
                    return it
                }
                throw ServiceException("null StateResponse...")
            } catch (e: Exception) {
                throw ServiceException("waiting StateResponse error: ${e.message}")
            }
        }
        throw ServiceException("No hello response from PLC yet...")
    }
}
