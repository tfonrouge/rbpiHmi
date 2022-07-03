package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.AppConfigFactory
import com.fonrouge.rbpiHmi.db.DatabaseFactory
import com.fonrouge.rbpiHmi.services.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

@Suppress("unused")
fun Application.main() {
    install(Compression)
    routing {
        applyRoutes(HelloServiceManager)
        applyRoutes(AppConfigServiceManager)
        applyRoutes(AuthServiceManager)
        applyRoutes(ConfigServiceManager)
        applyRoutes(HmiServiceManager)
    }
    kvisionInit()
    AppConfigFactory.init()
    DatabaseFactory.init()
}
