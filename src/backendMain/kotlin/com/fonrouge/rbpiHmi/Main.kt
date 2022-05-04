package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.AppConfigFactory
import com.fonrouge.rbpiHmi.data.DatabaseFactory
import com.fonrouge.rbpiHmi.services.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

@Suppress("unused")
fun Application.main() {
    AppConfigFactory.init()
    DatabaseFactory.init()
    install(Compression)
    routing {
        applyRoutes(AppConfigServiceManager)
        applyRoutes(AuthServiceManager)
        applyRoutes(ConfigServiceManager)
        applyRoutes(HmiServiceManager)
        applyRoutes(PingServiceManager)
        applyRoutes(PlcConfigServiceManager)
    }
    kvisionInit()
}
