package com.fonrouge.rbpiHmi.data

import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
class AppConfig(
    @EncodeDefault
    val commLinkConfig: CommLinkConfig = CommLinkConfig(),
    @EncodeDefault
    val sensorsConfig: SensorsConfig = SensorsConfig()
)
