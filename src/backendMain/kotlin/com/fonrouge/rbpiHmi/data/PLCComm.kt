package com.fonrouge.rbpiHmi.data

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListener
import com.fonrouge.rbpiHmi.dataComm.*
import com.fonrouge.rbpiHmi.services.HelloService.Companion.helloResponse
import io.kvision.remote.ServiceException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

object PLCComm {

    var commId = 0L

    var jsonElement: JsonElement? = null

    var serialCommConfig: SerialCommConfig? = null
        set(value) {
/*
            val s1 = try {
                Json.encodeToString(field)
            } catch (e: java.lang.Exception) {
                null
            }
            val s2 = try {
                Json.encodeToString(value)
            } catch (e: Exception) {
                null
            }
            if (s1 != s2) {
*/
            if (true) {
                serialPort = if (value != null) {
                    try {
                        val serialPort1 = SerialPort.getCommPort(value.serialPortPath)
                        serialPort1.setComPortParameters(
                            value.baudRate.toInt(),
                            value.dataBits,
                            value.stopBits,
                            value.parity
                        )
                        serialPort1
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    null
                }
                field = value
            }
        }

    var serialPort: SerialPort? = null
        set(value) {
            helloResponse = null
            field?.let { serialPort ->
                if (serialPort.isOpen) {
                    serialPort.closePort()
                }
            }
            field = establishComm(value)
        }

    fun getSerialPorts(): Array<out SerialPort>? {
        return SerialPort.getCommPorts()
    }

    private fun establishComm(serialPort1: SerialPort?): SerialPort? {
        serialPort1 ?: return null
        if (!serialPort1.openPort()) {
            println("error opening port...!")
            return null
        }
        serialPort1.addDataListener(SerialMessageListener())
        return serialPort1
    }

    private inline fun <reified T : IQuery> SerialPort.sendQuery(query: T): Int {
        if (query !is HelloQuery && helloResponse == null) {
            sendHelloQuery()
        }
        return if (query is HelloQuery || helloResponse != null) {
            val s = Json.encodeToString(query) + "\n"
            jsonElement = null
            val n = writeBytes(s.encodeToByteArray(), s.length.toLong())
//            println("SENT: $s")
            n
        } else 0
    }

    private inline fun <reified T> getResponse(): T? = runBlocking {
//        val millis = System.currentTimeMillis()
        val respMillis = AppConfigFactory.appConfig.commLinkConfig.hmiRefreshInterval + 1000L
        try {
//            println("WAIT RESPONSE FOR $respMillis millis")
            withTimeout(respMillis) {
                while (jsonElement == null) {
                    delay(10)
                }
            }
        } catch (e: Exception) {
            println("timeout error ${e.message}")
            helloResponse = null
            throw ServiceException("waiting response timeout error: ${e.message}")
        }
//        println("RESPONSE millis = ${System.currentTimeMillis() - millis} with jsonElement = $jsonElement")
        val result = jsonElement?.let {
//            println("jsonElement = $jsonElement")
            try {
                Json.decodeFromJsonElement<T>(it)
            } catch (e: Exception) {
                (it as? JsonObject)?.let { jsonObject ->
                    if (jsonObject.containsKey("hello")) {
                        helloResponse = null
                    }
                }
                throw ServiceException("SerialPort.getResponse decoding error: ${e.message}")
            }
        }
        jsonElement = null
        result
    }

    fun sendHelloQuery(): HelloResponse? {
        serialPort?.sendQuery(HelloQuery(AppConfigFactory.appConfig.sensorsConfig))
        helloResponse = getResponse()
        return helloResponse
    }

    fun sendStateQuery(): StateResponse? {
        serialPort?.sendQuery(StateQuery())
        return getResponse()
    }

    class SerialMessageListener : SerialPortMessageListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED
        }

        override fun serialEvent(event: SerialPortEvent?) {
            event?.receivedData?.let { bytes ->
                val s = String(bytes)
//                println("RECEIVED = $s")
                jsonElement = try {
                    Json.parseToJsonElement(String(bytes))
                } catch (e: Exception) {
                    print("NoJson($commId)> $s")
                    null
                }
            }
        }

        override fun getMessageDelimiter(): ByteArray {
            return "\n".encodeToByteArray()
        }

        override fun delimiterIndicatesEndOfMessage(): Boolean {
            return true
        }
    }
}
