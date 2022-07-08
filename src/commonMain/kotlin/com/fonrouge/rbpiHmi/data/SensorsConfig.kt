package com.fonrouge.rbpiHmi.data

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
@kotlinx.serialization.Serializable
class SensorsConfig(
    @EncodeDefault()
    val delayToSpinWindingRoller: Int = 0,
    @EncodeDefault()
    val additionalSpeedSpinToWindingRoller: Double = 5.0,
    @EncodeDefault()
    val delayToStopWindedRollerAfterCut: Int = 5000,
    @EncodeDefault()
    val spinUpUnWindedRollerBeforeAttaching: Boolean = true,
    @EncodeDefault()
    val startDetachingSignalPinNumber: Int = 34,
    @EncodeDefault()
    val startDetachingSignalInverter: Boolean = false,
    @EncodeDefault()
    val feederRollerRpmSignalPinNumber: Int = 35,
    @EncodeDefault()
    val feederRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val aWindingRollerRpmSignalPinNumber: Int = 36,
    @EncodeDefault()
    val aWindingRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val bWindingRollerRpmSignalPinNumber: Int = 39,
    @EncodeDefault()
    val bWindingRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val startAttachingSignalPinNumber: Int = 16,
    @EncodeDefault()
    val startAttachingSignalInverter: Boolean = false,
    @EncodeDefault()
    val cutOperationSignalPinNumber: Int = 17,
    @EncodeDefault()
    val cutOperationSignalInverter: Boolean = false,
    @EncodeDefault()
    val aMotorStartStopPinNumber: Int = 18,
    @EncodeDefault()
    val bMotorStartStopPinNumber: Int = 19,
    @EncodeDefault()
    val aMotorSpeedPinNumber: Int = 20,
    @EncodeDefault()
    val bMotorSpeedPinNumber: Int = 21,
)
