package com.fonrouge.rbpiHmi.data

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListener
import com.fonrouge.rbpiHmi.dataComm.*
import com.fonrouge.rbpiHmi.services.HelloService.Companion.helloResponse
import com.fonrouge.rbpiHmi.services.HmiService.Companion.stateResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

object PLCComm {

    var commId = 0L

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
        writeBytes(s.encodeToByteArray(), s.length.toLong())
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun sendHelloQuery() {
        if (!waitingHelloResponse) {
            waitingHelloResponse = true
            serialPort?.sendQuery(HelloQuery(AppConfigFactory.appConfig.sensorsConfig))
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun sendStateQuery() {
        serialPort?.sendQuery(StateQuery())
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun sendResetQuery() {
        helloResponse = null
        serialPort?.sendQuery(ResetQuery())
    }

    class SerialMessageListener : SerialPortMessageListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED
        }

        override fun serialEvent(event: SerialPortEvent?) {
            event?.receivedData?.let { bytes ->
                val string = String(bytes)
                val json = try {
                    Json.parseToJsonElement(string) as JsonObject
                } catch (e: Exception) {
                    null
                }
                when (val type = json?.get("type")?.jsonPrimitive?.contentOrNull) {
                    "Message" -> {
                        println(json.toString())
                    }
                    "RequestHello" -> {
                        waitingHelloResponse = false
                        sendHelloQuery()
                    }
                    "Error" -> {
                        println(json)
                    }
                    "Hello" -> {
                        println("Receiving hello response: $json")
                        helloResponse = Json.decodeFromJsonElement<HelloResponse>(json)
                    }
                    "State" -> {
                        stateResponse = try {
//                            println("STATE: $json")
                            Json.decodeFromJsonElement<StateResponse>(json)
                        } catch (e: Exception) {
                            println("decoding error: ${e.message}")
                            null
                        }
                    }
                    null -> {
                        if (string.isNotEmpty() && string != "\r\n") {
                            print("NoJson(${commId++})> $string")
                        }
                    }
                    else -> {
                        println("Unkown type: '$type'")
                    }
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
