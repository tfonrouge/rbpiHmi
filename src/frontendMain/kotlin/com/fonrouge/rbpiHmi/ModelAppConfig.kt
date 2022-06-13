package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.services.AppConfigService

object ModelAppConfig {
    private val appConfigService = AppConfigService()

    suspend fun appConfig(): AppConfig {
        return appConfigService.appConfig()
    }

    suspend fun writeAppConfig(appConfig: AppConfig): Boolean {
        return appConfigService.writeAppConfig(appConfig)
    }

    suspend fun hmiRefreshInterval(): Int {
        return appConfigService.appConfig().commLinkConfig.hmiRefreshInterval
    }
}
