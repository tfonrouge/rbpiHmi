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
    val delayToSpinEmptyRoller: Int = 0,
    @EncodeDefault()
    val delayToStopWindedRollerAfterCut: Int = 5000,
    @EncodeDefault()
    val spinUpUnWindedRollerBeforeAttaching: Boolean = true,
    @EncodeDefault()
    val startDetachingSignalPinNumber: Int = 5,
    @EncodeDefault()
    val startDetachingSignalInverter: Boolean = false,
    @EncodeDefault()
    val feederRollerRpmSignalPinNumber: Int = 15,
    @EncodeDefault()
    val feederRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val aWindingRollerRpmSignalPinNumber: Int = 2,
    @EncodeDefault()
    val aWindingRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val bWindingRollerRpmSignalPinNumber: Int = 4,
    @EncodeDefault()
    val bWindingRollerRpmSignalInverter: Boolean = false,
    @EncodeDefault()
    val attachedSignalPinNumber: Int = 18,
    @EncodeDefault()
    val attachedSignalInverter: Boolean = false,
    @EncodeDefault
    val detachingAttachingMaxTime: Int = 3000,
    @EncodeDefault()
    val cutOperationSignalPinNumber: Int = 19,
    @EncodeDefault()
    val cutOperationSignalInverter: Boolean = false,
    @EncodeDefault()
    val aMotorStartStopPinNumber: Int = 32,
    @EncodeDefault()
    val aMotorStartStopSignalInverter: Boolean = false,
    @EncodeDefault()
    val bMotorStartStopPinNumber: Int = 33,
    @EncodeDefault()
    val bMotorStartStopSignalInverter: Boolean = false,
    @EncodeDefault()
    val aMotorSpeedPinNumber: Int = 25,
    @EncodeDefault()
    val aMotorSpeedNominalRpm: Int = 1800,
    @EncodeDefault()
    val aMotorSpeedFinalRelation: Double = 0.5,
    @EncodeDefault()
    val aMotorSpeedNominalHertz: Double = 60.0,
    @EncodeDefault()
    val aMotorSpeedPwmChannel: Int = 0,
    @EncodeDefault()
    val aMotorSpeedPwmFreq: Int = 5000,
    @EncodeDefault()
    val aMotorSpeedPwmResolution: Int = 11,
    @EncodeDefault()
    val bMotorSpeedPinNumber: Int = 26,
    @EncodeDefault()
    val bMotorSpeedNominalRpm: Int = 1800,
    @EncodeDefault()
    val bMotorSpeedFinalRelation: Double = 0.5,
    @EncodeDefault()
    val bMotorSpeedNominalHertz: Double = 60.0,
    @EncodeDefault()
    val bMotorSpeedPwmChannel: Int = 1,
    @EncodeDefault()
    val bMotorSpeedPwmFreq: Int = 5000,
    @EncodeDefault()
    val bMotorSpeedPwmResolution: Int = 11,
)
