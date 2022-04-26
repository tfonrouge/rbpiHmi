package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.SerialComm
import io.kvision.remote.SimpleRemoteOption

actual class ConfigService : IConfigService {

    override suspend fun getSerialPortPathList(state: String?): List<SimpleRemoteOption> {
        val result = mutableListOf<SimpleRemoteOption>()
        SerialComm.getSerialPorts()?.forEach { serialPort ->
            result.add(
                SimpleRemoteOption(
                    value = serialPort.systemPortPath,
                    text = serialPort.systemPortPath
                )
            )
        }
        return result
    }
}
