@file:OptIn(ExperimentalSerializationApi::class)

package com.fonrouge.rbpiHmi.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

object AppConfigFactory {

    var appConfig: AppConfig = AppConfig()
        set(value) {
            PLCComm.serialCommConfig = value.commLinkConfig.serialCommConfig
            field = value
//            HelloService.helloResponse = null
            try {
                PLCComm.sendHelloQuery()
            } catch (e: Exception) {
                println("ERROR = ${e.message}")
            }
        }

    private const val propsFilename = "hmiApp.json"

    fun init() {
        readProperties()
    }

    fun readProperties() {
        appConfig = try {
            Json.decodeFromStream(FileInputStream(propsFilename))
        } catch (e: Exception) {
            AppConfig(
                commLinkConfig = CommLinkConfig(),
                sensorsConfig = SensorsConfig()
            )
        }
    }

    fun writeAppConfig(appConfig: AppConfig): Boolean {
        val s1 = try {
            Json.encodeToString(AppConfigFactory.appConfig)
        } catch (e: java.lang.Exception) {
            null
        }
        val s2 = try {
            Json.encodeToString(appConfig)
        } catch (e: Exception) {
            null
        }
        if (s1 != s2) {
            Json.encodeToStream(appConfig, FileOutputStream(propsFilename))
            AppConfigFactory.appConfig = appConfig
        }
        return true
    }

    init {
        val path = Paths.get(propsFilename)
        if (Files.notExists(path)) {
            Files.createFile(path)
        }
    }
}
