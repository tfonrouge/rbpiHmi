package com.fonrouge.rbpiHmi.data

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import com.fazecast.jSerialComm.SerialPortMessageListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PLCComm {

    var commId = 0L

    var stateResponse: StateResponse? = null

    var helloResponse: HelloResponse? = null

    var sJsonResponse: String? = null

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
        sJsonResponse = null
        return writeBytes(s.encodeToByteArray(), s.length.toLong())
    }

    private inline fun <reified T> getResponse(): T? = runBlocking {
        val millis = System.currentTimeMillis()
        withTimeout(50) {
            while (sJsonResponse == null) {
                delay(10)
            }
        }
        println("getResponse millis = ${System.currentTimeMillis() - millis}")
        sJsonResponse?.let {
            try {
                Json.decodeFromString<T>(it)
            } catch (e: Exception) {
                println("SerialPort.getResponse decoding error: ${e.message}")
                null
            }
        }
    }

    fun sendStateQuery(): Int? {
        stateResponse = null
        return serialPort?.sendQuery(
            StateQuery(
                commId = commId++,
                action = QueryAction.state
            )
        )
    }

    class SerialMessageListener : SerialPortMessageListener {
        override fun getListeningEvents(): Int {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED
        }

        override fun serialEvent(event: SerialPortEvent?) {
            event?.receivedData?.let { bytes ->
                sJsonResponse = String(bytes)
                println("event = $sJsonResponse")
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
