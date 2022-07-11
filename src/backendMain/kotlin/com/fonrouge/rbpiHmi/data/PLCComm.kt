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
import kotlinx.serialization.json.*

object PLCComm {

    var commId = 0L

    var jsonObject: JsonObject? = null

    var waitingHelloResponse = false

    var serialCommConfig: SerialCommConfig? = null
        set(value) {
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

    private inline fun <reified T : IQuery> SerialPort.sendQuery(query: T) {
        val s = Json.encodeToString(query) + "\n"
        jsonObject = null
        writeBytes(s.encodeToByteArray(), s.length.toLong())
    }

    private inline fun <reified T> getResponse(): T? = runBlocking {
//        val millis = System.currentTimeMillis()
        val respMillis = AppConfigFactory.appConfig.commLinkConfig.hmiRefreshInterval + 1000L
        try {
//            println("WAIT RESPONSE (${T::class.simpleName}) FOR $respMillis millis")
            withTimeout(respMillis) {
                while (jsonObject == null) {
                    delay(10)
                }
            }
        } catch (e: Exception) {
            val msg = "waiting (${T::class.simpleName}) response timeout error: ${e.message}"
            println(msg)
            throw ServiceException(msg)
        }
//        println("RESPONSE millis = ${System.currentTimeMillis() - millis} with jsonElement = $jsonObject")
        val result = jsonObject?.let {
//            println("jsonElement = $jsonObject")
            try {
                Json.decodeFromJsonElement<T>(it)
            } catch (e: Exception) {
                throw ServiceException("SerialPort.getResponse decoding error: ${e.message}")
            }
        }
        jsonObject = null
        result
    }

    fun sendHelloQuery(): HelloResponse? {
        if (!waitingHelloResponse) {
            waitingHelloResponse = true
            serialPort?.sendQuery(HelloQuery(AppConfigFactory.appConfig.sensorsConfig))
            helloResponse = getResponse()
        }
        return helloResponse
    }

    fun sendStateQuery(): StateResponse? {
        if (helloResponse != null) {
            serialPort?.sendQuery(StateQuery())
            return getResponse()
        }
        sendHelloQuery()
        return null
    }

    class SerialMessageListener : SerialPortMessageListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED
        }

        override fun serialEvent(event: SerialPortEvent?) {
            event?.receivedData?.let { bytes ->
                val string = String(bytes)
//                println("RECEIVED = $string")
                val result = try {
                    val json = Json.parseToJsonElement(string) as JsonObject
                    when (json["type"]?.jsonPrimitive?.contentOrNull) {
                        "Message" -> {
                            val messageResponse = Json.decodeFromJsonElement<MessageResponse>(json)
                            println("[MSG #${messageResponse.commId}] [${messageResponse.msgType}] ${messageResponse.message}")
                            null
                        }
                        "RequestHello" -> {
                            waitingHelloResponse = false
                            sendHelloQuery()
                            null
                        }
                        "Error" -> {
                            println(json.toString())
                            null
                        }
                        else -> json
                    }
                } catch (e: Exception) {
                    if (string.isNotEmpty()) {
                        print("NoJson($commId)> $string")
                    }
                    null
                }
                result?.let {
                    jsonObject = it
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
