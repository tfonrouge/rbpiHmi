package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.AppConfig
import com.fonrouge.rbpiHmi.data.AppConfigFactory

actual class AppConfigService : IAppConfigService {

    override suspend fun appConfig(): AppConfig {
        return AppConfigFactory.appConfig
    }

    override suspend fun writeAppConfig(appConfig: AppConfig): Boolean {
        return AppConfigFactory.writeAppConfig(appConfig)
    }
}
