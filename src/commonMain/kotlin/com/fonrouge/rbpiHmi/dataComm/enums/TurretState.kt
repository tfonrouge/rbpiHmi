package com.fonrouge.rbpiHmi.dataComm.enums

enum class TurretState(
    val imageSrc: String,
) {
    winding("static-rotating-circle.png"),
    detaching("circle-arrow-right.png"),
    cutting("scissor.png"),
    attached("circle-arrow-left.png")
}
