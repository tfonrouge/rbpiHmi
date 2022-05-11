package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.ContainerPLCState
import io.kvision.annotations.KVService

@KVService
interface IHmiService {
    suspend fun getState(): ContainerPLCState
}
