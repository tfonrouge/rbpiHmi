package com.fonrouge.rbpiHmi.enums

enum class RollerFeedPosition(
    val imageSrc: String,
) {
    Feeding("static-rotating-circle.png"),
    Detaching("circle-arrow-right.png"),
    Cutting("scissor.png"),
    Attaching("circle-arrow-left.png")
}
