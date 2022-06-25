@file:OptIn(ExperimentalSerializationApi::class)

package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.dataComm.ConfigQuery
import kotlinx.serialization.ExperimentalSerializationApi
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
            try {
                PLCComm.sendConfigQuery(ConfigQuery(value.sensorsConfig))
            } catch (e: Exception) {
                println("sendConfigQuery error: ${e.message}")
            }
            field = value
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
        Json.encodeToStream(appConfig, FileOutputStream(propsFilename))
        AppConfigFactory.appConfig = appConfig
        return true
    }

    init {
        val path = Paths.get(propsFilename)
        if (Files.notExists(path)) {
            Files.createFile(path)
        }
    }
}
