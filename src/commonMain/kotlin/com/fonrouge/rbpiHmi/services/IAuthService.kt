package com.fonrouge.rbpiHmi.services

import io.kvision.annotations.KVService

@KVService
interface IAuthService {
    suspend fun authenticate(password: String): Boolean
}
