package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.data.PLCComm
import io.kvision.remote.SimpleRemoteOption

actual class ConfigService : IConfigService {

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
        return listOf(
            SimpleRemoteOption("300", "300 baud"),
            SimpleRemoteOption("1200", "1200 baud"),
            SimpleRemoteOption("2400", "2400 baud"),
            SimpleRemoteOption("4800", "4800 baud"),
            SimpleRemoteOption("9600", "9600 baud"),
            SimpleRemoteOption("19200", "19200 baud"),
            SimpleRemoteOption("38400", "38400 baud"),
            SimpleRemoteOption("57600", "57600 baud"),
            SimpleRemoteOption("74880", "74880 baud"),
            SimpleRemoteOption("115200", "115200 baud"),
            SimpleRemoteOption("230400", "230400 baud"),
            SimpleRemoteOption("250000", "250000 baud"),
            SimpleRemoteOption("500000", "500000 baud"),
        )
    }
}
