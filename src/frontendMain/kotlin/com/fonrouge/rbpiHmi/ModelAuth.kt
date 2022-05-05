package com.fonrouge.rbpiHmi

import com.fonrouge.rbpiHmi.services.AuthService

object ModelAuth {

    private val authService = AuthService()

    suspend fun authenticate(password: String): Boolean {
        return authService.authenticate(password)
    }
}
