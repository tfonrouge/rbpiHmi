package com.fonrouge.rbpiHmi

import com.fazecast.jSerialComm.SerialPort

object SerialComm {

    var serialPort: SerialPort? = null

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
