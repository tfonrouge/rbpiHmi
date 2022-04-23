package com.fonrouge.rbpiHmi.data

import com.fonrouge.rbpiHmi.enums.RollerFeedPosition

@kotlinx.serialization.Serializable
class HmiState(
    val rollerFeedPosition: RollerFeedPosition,
    val rollerFeedState: RollerFeedState,
)
