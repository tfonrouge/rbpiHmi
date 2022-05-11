package com.fonrouge.rbpiHmi.enums

enum class RollerFeedPosition(
    val imageSrc: String,
) {
    feeding("static-rotating-circle.png"),
    detaching("circle-arrow-right.png"),
    cutting("scissor.png"),
    attaching("circle-arrow-left.png")
}
