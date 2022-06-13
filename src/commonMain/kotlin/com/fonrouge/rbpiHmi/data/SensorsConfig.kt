package com.fonrouge.rbpiHmi.data

@kotlinx.serialization.Serializable
class SensorsConfig(
    val delayToSpinWindingRoller: Int = 0,
    val additionalSpeedSpinToWindingRoller: Double = 5.0,
    val delayToStopWindedRollerAfterCut: Int = 5000,
    val spinUnWindedRollerBeforeAttaching: Boolean = true,
    val inputSignalStartDetaching: Int = 34,
    val inputSignalFeederRollerRpm: Int = 35,
    val inputSignalWindingRollerRpm: Int = 36,
    val inputSignalStartAttaching: Int = 39,
    val inputSignalCutOperation: Int =
)
