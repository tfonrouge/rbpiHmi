package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.ContainerPLCState
import com.fonrouge.rbpiHmi.data.PLCComm
import com.fonrouge.rbpiHmi.data.PLCComm.stateResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

actual class HmiService : IHmiService {

    override suspend fun getState(): ContainerPLCState {
        var containerPLCState: ContainerPLCState? = null
        println("sending StateQuery")
        PLCComm.sendStateQuery()?.let {
            println("StateQuery send")
            try {
                withTimeout(100) {
                    println("wait for response")
                    while (stateResponse == null) {
                        delay(10)
                    }
                    println("responded with stateResponse")
                }
            } catch (e: Exception) {
                println("error: ${e.message}")
            }
            println("stateResponse = $stateResponse")
            stateResponse?.let {
                containerPLCState = ContainerPLCState(
                    valid = true,
                    stateResponse = it
                )
            }
/*
            coroutineScope {
                val millis = currentTimeMillis()
                val job = launch(Dispatchers.IO) {
                    println("wait for response")
                    while (stateResponse == null && currentTimeMillis() - millis < 500) {

                    }
                    containerPLCState = ContainerPLCState(
                        valid = true,
                        stateResponse = stateResponse
                    )
                    println("responded")
                }
                job.join()
            }
*/
        }
        println("returning")
        return containerPLCState ?: ContainerPLCState(valid = false, stateResponse = null)
    }
}
