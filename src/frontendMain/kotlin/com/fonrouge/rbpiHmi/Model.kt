package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.tables.User

object Model {

    val user: User? = null

    private val pingService = PingService()
    private val authService = AuthService()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

    suspend fun auth(password: String): Boolean {
        return authService.authenticate(password)
    }
}
