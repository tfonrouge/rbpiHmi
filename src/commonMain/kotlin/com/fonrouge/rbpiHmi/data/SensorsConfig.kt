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
    val startDetachingSignalInverter: Boolean = true,
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
    val startAttachingSignalInverter: Boolean = true,
    @EncodeDefault()
    val cutOperationSignalPinNumber: Int = 17,
    @EncodeDefault()
    val cutOperationSignalInverter: Boolean = true,
    @EncodeDefault()
    val aMotorStartStopPinNumber: Int = 18,
    @EncodeDefault()
    val aMotorStartStopSignalInverter: Boolean = true,
    @EncodeDefault()
    val bMotorStartStopPinNumber: Int = 19,
    @EncodeDefault()
    val bMotorStartStopSignalInverter: Boolean = true,
    @EncodeDefault()
    val aMotorSpeedPinNumber: Int = 21,
    @EncodeDefault()
    val aMotorSpeedNominalRpm: Int = 1800,
    @EncodeDefault()
    val aMotorSpeedNominalHertz: Int = 60,
    @EncodeDefault()
    val aMotorSpeedPwmChannel: Int = 0,
    @EncodeDefault()
    val aMotorSpeedPwmFreq: Int = 5000,
    @EncodeDefault()
    val aMotorSpeedPwmResolution: Int = 11,
    @EncodeDefault()
    val bMotorSpeedPinNumber: Int = 21,
    @EncodeDefault()
    val bMotorSpeedNominalRpm: Int = 1800,
    @EncodeDefault()
    val bMotorSpeedNominalHertz: Int = 60,
    @EncodeDefault()
    val bMotorSpeedPwmChannel: Int = 1,
    @EncodeDefault()
    val bMotorSpeedPwmFreq: Int = 5000,
    @EncodeDefault()
    val bMotorSpeedPwmResolution: Int = 11,
)
