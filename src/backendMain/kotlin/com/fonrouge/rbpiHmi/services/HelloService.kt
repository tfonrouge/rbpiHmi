package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import io.kvision.remote.ServiceException

actual class HelloService : IHelloService {

    companion object {
        var helloResponse: HelloResponse? = null
            set(value) {
                field = value
                if (value != null) {
                    println("HelloResponse received...")
                }
                if (PLCComm.waitingHelloResponse) {
                    PLCComm.waitingHelloResponse = false
                }
            }
    }

    override suspend fun getHelloResponse(): HelloResponse {
        helloResponse?.let {
            return it
        }
        throw ServiceException("No hello response yet from PLC...")
    }
}
