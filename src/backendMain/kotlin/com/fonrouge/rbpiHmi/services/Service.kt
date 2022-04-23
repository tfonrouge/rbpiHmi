package com.fonrouge.rbpiHmi.services

actual class PingService : IPingService {

    override suspend fun ping(message: String): String {
        println(message)
        return "Hello world from server!"
    }
}
