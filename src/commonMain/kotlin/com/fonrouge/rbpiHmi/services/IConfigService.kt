package com.fonrouge.rbpiHmi.services

import io.kvision.annotations.KVService
import io.kvision.remote.SimpleRemoteOption

@KVService
interface IConfigService {
    suspend fun getSerialPortUrlList(
        state: String?,
    ): List<SimpleRemoteOption>
}
