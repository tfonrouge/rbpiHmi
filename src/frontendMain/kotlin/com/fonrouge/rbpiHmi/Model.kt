package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.services.PingService

object Model {

    private val pingService = PingService()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }
}
