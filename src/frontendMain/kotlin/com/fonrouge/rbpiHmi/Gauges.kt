package com.fonrouge.rbpiHmi

import react.ComponentClass
import react.PropsWithChildren

external interface ReactCanvasGaugesProps : PropsWithChildren {
    var colorPlate: dynamic
    var width: dynamic
    var height: dynamic
    var units: String
    var title: dynamic
    var value: Number
    var minValue: Number
    var maxValue: Number
    var exactTicks: Boolean
    var majorTicks: Array<Number>
    var minorTicks: dynamic
    var highlights: dynamic
}

@Suppress("UnsafeCastFromDynamic")
val RadialGauge: ComponentClass<ReactCanvasGaugesProps> = io.kvision.require("react-canvas-gauges").RadialGauge

//external class RadialGauge
