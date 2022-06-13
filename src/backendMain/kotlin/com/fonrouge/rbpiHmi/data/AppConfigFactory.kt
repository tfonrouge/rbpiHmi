@file:OptIn(ExperimentalSerializationApi::class)

package com.fonrouge.rbpiHmi.data

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

    fun writeProperties(appConfig: AppConfig): Boolean {
        Json.encodeToStream(appConfig, FileOutputStream(propsFilename))
        this.appConfig = appConfig
        return true
    }

    init {
        val path = Paths.get(propsFilename)
        if (Files.notExists(path)) {
            Files.createFile(path)
        }
    }
}
