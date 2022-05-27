package com.fonrouge.rbpiHmi.dataComm.enums

enum class TurretState(
    val imageSrc: String,
) {
    feeding("static-rotating-circle.png"),
    detaching("circle-arrow-right.png"),
    cutting("scissor.png"),
    attaching("circle-arrow-left.png")
}
