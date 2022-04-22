package com.fonrouge.rbpiHmi

import io.kvision.annotations.KVService

@KVService
interface IAuthService {
    suspend fun authenticate(password: String): Boolean
}
