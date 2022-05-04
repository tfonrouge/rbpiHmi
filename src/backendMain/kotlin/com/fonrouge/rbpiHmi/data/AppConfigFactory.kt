package com.fonrouge.rbpiHmi.data

import io.ktor.server.application.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.Path

object AppConfigFactory {

    lateinit var appConfig: AppConfig

    private const val propsFilename = "hmiApp.json"

    fun init() {
        readProperties()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun readProperties() {
        appConfig = try {
            Json.decodeFromStream(FileInputStream(propsFilename))
        } catch (e: Exception) {
            AppConfig()
        }
        SerialComm.serialPortPath = appConfig.serialPortPath
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun writeProperties(appConfig: AppConfig): Boolean {
        Json.encodeToStream(appConfig, FileOutputStream(propsFilename))
        this.appConfig = appConfig
        SerialComm.serialPortPath = appConfig.serialPortPath
        return true
    }

    init {
        val path = Paths.get(propsFilename)
        if (Files.notExists(path)) {
            Files.createFile(path)
        }
    }
}
