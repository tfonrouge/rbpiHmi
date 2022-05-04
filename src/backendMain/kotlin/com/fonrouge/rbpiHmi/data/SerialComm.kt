package com.fonrouge.rbpiHmi.data

import com.fazecast.jSerialComm.SerialPort

object SerialComm {

    var serialPort: SerialPort? = null

    fun setSerialValues(appConfig: AppConfig) {
        serialPort = SerialPort.getCommPort(appConfig.serialPortPath).apply {
            baudRate = appConfig.baudRate.toInt()
        }
    }

    var serialPortPath: String? = null
        set(value) {
            if (field != value) {
                field = value
                serialPort = SerialPort.getCommPort(value)
            }
        }

    fun getSerialPorts(): Array<out SerialPort>? {
        return SerialPort.getCommPorts()
    }

    fun read(): ByteArray? {
        serialPort?.let { serialPort ->
            val numBytes = serialPort.bytesAvailable()
            if (numBytes > 0) {
                val readBuffer = ByteArray(numBytes)
                val numRead = serialPort.readBytes(readBuffer, numBytes.toLong())
                return readBuffer
            }
        }
        return null
    }

    init {
        SerialPort.getCommPorts()?.forEach {
            println("Serial port: $it")
        }
    }
}
