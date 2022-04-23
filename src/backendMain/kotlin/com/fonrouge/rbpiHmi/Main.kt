package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.services.AuthServiceManager
import com.fonrouge.rbpiHmi.services.ConfigServiceManager
import com.fonrouge.rbpiHmi.services.HmiServiceManager
import com.fonrouge.rbpiHmi.services.PingServiceManager
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.kvision.remote.applyRoutes
import io.kvision.remote.kvisionInit

fun Application.main() {
    install(Compression)
    routing {
        applyRoutes(PingServiceManager)
        applyRoutes(AuthServiceManager)
        applyRoutes(HmiServiceManager)
        applyRoutes(ConfigServiceManager)
    }
    kvisionInit()
}
