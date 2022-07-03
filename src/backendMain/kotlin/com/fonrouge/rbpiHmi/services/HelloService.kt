package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import io.kvision.remote.ServiceException

actual class HelloService : IHelloService {

    companion object {
        var helloResponse: HelloResponse? = null
    }

    override suspend fun getHelloResponse(): HelloResponse {
        helloResponse?.let {
            return it
        }
        return PLCComm.sendHelloQuery()?.let {
            helloResponse = it
            it
        } ?: throw ServiceException("hello response error from PLC...")
    }
}
