package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import io.kvision.annotations.KVService

@KVService
interface IHelloService {

    suspend fun getHelloResponse() : HelloResponse

}
