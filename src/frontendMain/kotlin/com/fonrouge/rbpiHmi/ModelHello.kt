package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import com.fonrouge.rbpiHmi.services.HelloService
import io.kvision.state.ObservableValue
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition

object ModelHello {

    private val helloService = HelloService()
    val helloResponseObservableValue = ObservableValue<HelloResponse?>(null)

    suspend fun getHelloResponse() {
        try {
            helloResponseObservableValue.value = helloService.getHelloResponse().also {
                Toast.info(
                    message = "PLC: <b>${it.item}</b><br>Maker: <b>${it.maker}</b><br>Email: <b>${it.email}</b>",
                    title = "Hello",
                    options = ToastOptions(
                        positionClass = ToastPosition.TOPFULLWIDTH,
                        progressBar = true
                    )
                )
            }
        } catch (e: Exception) {
            Toast.error(
                message = e.message ?: "?",
                title = "getHelloResponse() error:",
                options = ToastOptions(
                    preventDuplicates = true
                )
            )
        }
    }
}
