package com.fonrouge.rbpiHmi.services

import com.fonrouge.rbpiHmi.SerialComm
import io.kvision.remote.SimpleRemoteOption

actual class ConfigService : IConfigService {

    override suspend fun getSerialPortUrlList(state: String?): List<SimpleRemoteOption> {
        val result = mutableListOf<SimpleRemoteOption>()
        SerialComm.getSerialPorts()?.forEach {
            result.add(
                SimpleRemoteOption(
                    value = it.portLocation,
                    text = it.descriptivePortName
                )
            )
        }
        return result
    }
}
