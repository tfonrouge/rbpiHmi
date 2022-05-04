package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.AppConfig
import io.kvision.annotations.KVService

@KVService
interface IAppConfigService {
    suspend fun appConfig(): AppConfig
    suspend fun writeAppConfig(appConfig: AppConfig): Boolean
}
