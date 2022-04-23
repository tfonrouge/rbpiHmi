package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.HmiState
import io.kvision.annotations.KVService

@KVService
interface IHmiService {
    suspend fun getState(): HmiState
}
