package com.fonrouge.rbpiHmi.services

actual class AuthService : IAuthService {
    override suspend fun authenticate(password: String): Boolean {
        return password == "0000"
    }
}
