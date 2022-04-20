package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.tables.User

object Model {

    val user: User? = null

    private val pingService = PingService()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }
}
