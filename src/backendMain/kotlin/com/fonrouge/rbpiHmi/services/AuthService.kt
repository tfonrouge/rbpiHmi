package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.AppConfigFactory

actual class AuthService : IAuthService {
    override suspend fun authenticate(password: String): Boolean {
        return password == AppConfigFactory.appConfig.numericPassword
    }
}
