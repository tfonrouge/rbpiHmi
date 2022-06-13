package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class AppConfig(
    val commLinkConfig: CommLinkConfig = CommLinkConfig(),
    val sensorsConfig: SensorsConfig = SensorsConfig()
)
