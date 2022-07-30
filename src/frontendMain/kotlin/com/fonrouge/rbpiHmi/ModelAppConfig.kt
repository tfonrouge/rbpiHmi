package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.services.AppConfigService

object ModelAppConfig {

    val appConfigService = AppConfigService()

    var appConfig: AppConfig = AppConfig()

    suspend fun getAppConfig(): AppConfig {
        appConfig = appConfigService.appConfig()
        return appConfig
    }

    suspend fun writeAppConfig(appConfig: AppConfig): Boolean {
        return appConfigService.writeAppConfig(appConfig)
    }

    suspend fun hmiRefreshInterval(): Int {
        return appConfigService.appConfig().commLinkConfig.hmiRefreshInterval
    }
}
