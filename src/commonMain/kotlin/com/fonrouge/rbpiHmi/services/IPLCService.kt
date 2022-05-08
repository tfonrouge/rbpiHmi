package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCConfig
import io.kvision.annotations.KVService

@KVService
interface IPLCService {

    suspend fun plcConfigs(): List<PLCConfig>

}
