package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import io.kvision.remote.ServiceException

actual class HelloService : IHelloService {

    companion object {
        var helloResponse: HelloResponse? = null
    }

    override suspend fun getHelloResponse(): HelloResponse {
        return try {
            PLCComm.sendHelloQuery().let {
                helloResponse = it
                it
            }
        } catch (e: Exception) {
            throw ServiceException("getHelloResponse() error: ${e.message}")
        }
    }
}
