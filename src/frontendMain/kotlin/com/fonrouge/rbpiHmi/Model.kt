package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.data.HmiState
import com.fonrouge.rbpiHmi.services.AuthService
import com.fonrouge.rbpiHmi.services.HmiService
import com.fonrouge.rbpiHmi.services.PingService
import com.fonrouge.rbpiHmi.tables.User

object Model {

    val user: User? = null

    private val pingService = PingService()
    private val authService = AuthService()
    private val hmiService = HmiService()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

    suspend fun auth(password: String): Boolean {
        return authService.authenticate(password)
    }

    suspend fun hmiServiceGetState() : HmiState {
        return hmiService.getState()
    }
}
