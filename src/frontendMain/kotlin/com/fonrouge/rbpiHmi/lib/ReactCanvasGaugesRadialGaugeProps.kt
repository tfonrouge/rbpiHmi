package com.fonrouge.rbpiHmi.lib

import react.ComponentClass

external interface ReactCanvasGaugesRadialGaugeProps : ReactCanvasGaugesCommonProps {
    /* Ticks Bar Options */
    var ticksAngle: dynamic         /*defines a max angle for ticks bar. By default, is 270 degrees. If 360 degrees specified ticks bar fills the whole circle.*/
    var startAngle: dynamic         /*defines a start angle using which ticks bar starts. By default, is 45 degrees.*/
    var barStartPosition: dynamic   /*‘left’	‘right’. By default, is ‘left’. Enable anti-clockwise progress bars and middle start point progress bars.*/

    /* Coloring Options */
    var colorNeedleCircleOuter: dynamic /*defines a color which should be used to draw outer decorative circle element at the middle of the gauge.*/
    var colorNeedleCircleOuterEnd: dynamic  /*if defined, outer decorative circle gauge element will be drawn as gradient. If falsy - outer circle will be drawn using solid color.*/
    var colorNeedleCircleInner: dynamic /*defines a color which should be used to draw inner decorative circle element at the middle of the gauge.*/
    var colorNeedleCircleInnerEnd: dynamic  /*if defined, inner decorative circle gauge element will be drawn as gradient. If falsy - inner circle will be drawn using solid color.*/

    /* Needle Options */
    var needleCircleSize: dynamic   /*the size in relative units of the decorative circles element of the gauge.*/
    var needleCircleInner: Boolean  /*a boolean flag, turns on/off inner decorative circle element drawing.*/
    var needleCircleOuter: Boolean  /*a boolean flag, turns on/off outer decorative circle element drawing.*/

    /* Animation Options */
    var animationTarget: dynamic    /*defines which part of the gauge should be animated when changing the value. Could be one of ‘needle’ (default) or ‘plate’ values. When ‘plate’ is selected then gauge will animate ticks bar instead of animating the needle.*/
    var useMinPath: dynamic         /*boolean. Applicable only to radial gauges which have full 360-degree ticks plate. If set to true for this kind of gauges will rotate needle/plate by a minimal rotation path.*/
}

@Suppress("UnsafeCastFromDynamic")
val RadialGauge: ComponentClass<ReactCanvasGaugesRadialGaugeProps> =
    io.kvision.require("react-canvas-gauges").RadialGauge
