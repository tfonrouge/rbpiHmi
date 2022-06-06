package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import io.kvision.remote.SimpleRemoteOption

actual class ConfigService : IConfigService {

    companion object {
        val baudrateList = listOf(
            300,
            600,
            1200,
            1800,
            2400,
            4800,
            9600,
            19200,
            28800,
            38400,
            57600,
            76800,
            115200,
            230400,
            460800,
            576000,
            921600
        )
    }

    override suspend fun getSerialPortPathList(state: String?): List<SimpleRemoteOption> {
        val result = mutableListOf(SimpleRemoteOption("", ""))
        PLCComm.getSerialPorts()?.forEach { serialPort ->
            result.add(
                SimpleRemoteOption(
                    value = serialPort.systemPortPath,
                    text = serialPort.systemPortPath
                )
            )
        }
        return result
    }

    override suspend fun getBaudRateList(state: String?): List<SimpleRemoteOption> {
        return baudrateList.map { SimpleRemoteOption(it.toString(), "$it baud") }
    }
}
