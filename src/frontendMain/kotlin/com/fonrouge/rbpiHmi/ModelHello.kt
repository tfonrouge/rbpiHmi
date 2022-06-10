package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import com.fonrouge.rbpiHmi.services.HelloService
import io.kvision.state.ObservableValue

object ModelHello {

    private val helloService = HelloService()
    val helloResponseObservableValue = ObservableValue<HelloResponse?>(null)

    suspend fun getHelloResponse() {
        console.warn("calling to get hello response")
        try {
            val helloResponse = helloService.getHelloResponse()
            console.warn("helloResponse", helloResponse)
            helloResponseObservableValue.value = helloResponse
        } catch (e: Exception) {
            console.error("getHello response service", e.message)
        }
    }
}
