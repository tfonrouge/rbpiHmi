package com.fonrouge.rbpiHmi.services

import io.kvision.annotations.KVService

@KVService
interface IPingService {
    suspend fun ping(message: String): String
}
