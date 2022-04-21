package com.fonrouge.rbpiHmi

import react.ComponentClass
import react.PropsWithChildren

external interface ReactCanvasGaugesProps : PropsWithChildren {
    var width: Int
    var height: Int
    var units: String
    var title: dynamic
    var minValue: Number
    var maxValue: Number
}

val ReactCanvasGauges: ComponentClass<ReactCanvasGaugesProps> = io.kvision.require("react-canvas-gauges").RadialGauge

//external class RadialGauge
