package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm.helloResponse
import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import io.kvision.remote.ServiceException

actual class HelloService : IHelloService {

    override suspend fun getHelloResponse(): HelloResponse {
        return helloResponse ?: throw ServiceException("Hello response is NULL ... !")
    }
}
