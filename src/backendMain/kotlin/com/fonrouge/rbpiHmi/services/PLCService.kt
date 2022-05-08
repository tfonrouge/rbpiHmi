package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCConfig

actual class PLCService : IPLCService {
    override suspend fun plcConfigs(): List<PLCConfig> {
        TODO("Not yet implemented")
    }
}
