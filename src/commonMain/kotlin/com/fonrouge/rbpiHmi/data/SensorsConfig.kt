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
    val startDetachingSignalHighState: Int = 1,
    @EncodeDefault()
    val feederRollerRpmSignalPinNumber: Int = 35,
    @EncodeDefault()
    val feederRollerRpmSignalHighState: Int = 1,
    @EncodeDefault()
    val aWindingRollerRpmSignalPinNumber: Int = 36,
    @EncodeDefault()
    val aWindingRollerRpmSignalHighState: Int = 1,
    @EncodeDefault()
    val bWindingRollerRpmSignalPinNumber: Int = 39,
    @EncodeDefault()
    val bWindingRollerRpmSignalHighState: Int = 1,
    @EncodeDefault()
    val startAttachingSignalPinNumber: Int = 16,
    @EncodeDefault()
    val startAttachingSignalHighState: Int = 1,
    @EncodeDefault()
    val CutOperationSignalPinNumber: Int = 17,
    @EncodeDefault()
    val CutOperationSignalHighState: Int = 1,
)
