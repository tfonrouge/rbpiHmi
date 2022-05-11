package com.fonrouge.rbpiHmi.data

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListener
import com.fonrouge.rbpiHmi.dataComm.HelloQuery
import com.fonrouge.rbpiHmi.dataComm.HelloResponse
import com.fonrouge.rbpiHmi.dataComm.StateQuery
import com.fonrouge.rbpiHmi.dataComm.StateResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

object PLCComm {

    var commId = 0L

    var helloResponse: HelloResponse? = null

    var jsonElement: JsonElement? = null

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
        serialPort1.sendQuery(
            HelloQuery(
                commId = commId++,
                action = QueryAction.hello
            )
        )
        helloResponse = getResponse()
        println("hello response = $helloResponse")
        return serialPort1
    }

    private inline fun <reified T> SerialPort.sendQuery(query: T): Int {
        val s = Json.encodeToString(query) + "\n"
        jsonElement = null
        return writeBytes(s.encodeToByteArray(), s.length.toLong())
    }

    private inline fun <reified T> getResponse(): T? = runBlocking {
        val millis = System.currentTimeMillis()
        try {
            withTimeout(50) {
                while (jsonElement == null) {
                    delay(10)
                }
            }
        } catch (e: java.lang.Exception) {
            println("timeout error ${e.message}")
        }
        println("getResponse millis = ${System.currentTimeMillis() - millis}")
        val result = jsonElement?.let {
            println("jsonElement = $jsonElement")
            try {
                Json.decodeFromJsonElement<T>(it)
            } catch (e: Exception) {
                println("SerialPort.getResponse decoding error: ${e.message}")
                null
            }
        }
        jsonElement = null
        result
    }

    fun sendStateQuery(): StateResponse? {
        serialPort?.sendQuery(
            StateQuery(
                commId = commId++,
                action = QueryAction.state
            )
        )
        return getResponse()
    }

    class SerialMessageListener : SerialPortMessageListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED
        }

        override fun serialEvent(event: SerialPortEvent?) {
            event?.receivedData?.let { bytes ->
                jsonElement = try {
                    Json.parseToJsonElement(String(bytes))
                } catch (e: Exception) {
                    null
                }
            }
        }

        override fun getMessageDelimiter(): ByteArray {
            return "\n".encodeToByteArray()
        }

        override fun delimiterIndicatesEndOfMessage(): Boolean {
            return false
        }
    }
}
