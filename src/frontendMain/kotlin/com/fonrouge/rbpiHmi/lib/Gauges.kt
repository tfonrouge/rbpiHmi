package com.fonrouge.rbpiHmi.lib

import react.ComponentClass
import react.PropsWithChildren

external interface ReactCanvasGaugesCommonProps : PropsWithChildren {

    /*
    docs at: https://canvas-gauges.com/documentation/user-guide/configuration
     */

    /* basic options */
    var width: dynamic
    var height: dynamic
    var minValue: Number
    var maxValue: Number
    var value: Number
    var units: String
    var title: dynamic

    /* ticks bar options */
    var exactTicks: Boolean
    var majorTicks: Array<Number>
    var minorTicks: dynamic
    var strokeTicks: Boolean
    var majorTicksInt: Int
    var majorTicksDec: Int
    var highlights: dynamic         /*an array of highlights objects, which configures color-highlighted areas on a ticks bar. Each highlight object defines an area to colorize starting from value to value and using a given color, like this: { from: number, to: number, color: string }*/
    var highlightsWidth: dynamic    /*sets the width of highlights area in relative units.*/
    var numbersMargin: dynamic      /*defines a margin for tick labels (numbers) in relative units. By default, is 1.*/

    /* Progress Bar Options */
    var barWidth: dynamic           /*bar width in percents in relation to overall width of the gauge. It is limited to 50% anyway.*/
    var barStrokeWidth: dynamic     /*defines a width of a bar stroke. If set to zero - stroke won’t be drawn.*/
    var barProgress: dynamic        /*flag, defines if a progress bar should be drawn within this gauge.*/
    var barShadow: Number           /*number, length of the inner bar shadow if required. By default, is 0.*/

    /* Animation Options */
    var animation: Boolean          /*boolean flag signaling whenever the animation is possible on the gauge or not.*/
    var animationDuration: Number   /*time in milliseconds of the animation duration.*/
    var animationRule: dynamic      /*defines a type of animation behavior for the gauge. Canvas gauges already knows the most used types of animation rules, or you can define your own animation rule providing the animation rule function within this option. Known rules could be bypassed as string names, which are: “linear”, “quad”, “quint”, “cycle”, “bounce”, “elastic” and their opposites: “dequad”, “dequint”, “decycle”, “debounce”, “delastic”.*/
    var animatedValue: Boolean      /*a boolean flag, specifies if a value displayed in a value box of the gauge should be constantly updated during animation run. By default, it is falsy, so the upset gauge value will be shown immediately and animation will run visually only on the gauge needle or progress bar.*/
    var animateOnInit: Boolean      /*a boolean flag, which specifies if gauge should be animated on the first draw, by default is false.*/

    /* Coloring Options */
    var colorPlate: dynamic         /*defines background color of the gauge plate.*/
    var colorPlateEnd: dynamic      /*if specified wil use gradient fill for the plate.*/
    var colorMajorTicks: dynamic    /*color of the major ticks lines (also applied to stroke if strokeTicks option is true). It can be an array of colors, for each major tick it is possible to specify specific color. In this case if strokeTicks enabled, the first color from this array will be used for stroking.*/
    var colorMinorTicks: dynamic    /*color of the minor ticks lines.*/
    var colorStrokeTicks: dynamic   /*defines a static color for all ticks lines. By default, is not specified. If set to some color value will override major ticks stroke color for lines, but will not influence numbers colors. For minor ticks will be used if colorMinorTicks is not specified.*/
    var colorTitle: dynamic         /*color of the title text.*/
    var colorUnits: dynamic         /*color of the units text.*/
    var colorNumbers: dynamic       /*color of the text for the tick numbers. It can be an array of colors, containing specific color for each number.*/
    var colorNeedle: dynamic        /*defines color of the gauge needle.*/
    var colorNeedleEnd: dynamic     /*if defined it enables use of gradient for the gauge needle. If this is falsy, needle will be draw using solid color.*/
    var colorValueText: dynamic     /*defines a color of the text in a value box.*/
    var colorValueTextShadow: dynamic   /*defines a color of a text in a value box. If this value is falsy shadow won’t be drawn.*/
    var colorBorderShadow: dynamic  /*defines a shadow color of the gauge plate. If is falsy the shadow won’t be drawn.*/
    var colorBorderOuter: dynamic   /*defines a color of the outer border for the gauge plate.*/
    var colorBorderOuterEnd: dynamic    /*if defined it enables use of gradient on the outer border.*/
    var colorBorderMiddle: dynamic  /*defines a color of the middle border for the gauge plate.*/
    var colorBorderMiddleEnd: dynamic   /*defined it enables use of gradient on the middle border.*/
    var colorBorderInner: dynamic   /*defines a color of the inner border for the gauge plate.*/
    var colorBorderInnerEnd: dynamic    /*if defined it enables use of gradient on the inner border.*/
    var colorValueBoxRect: dynamic  /*defines a color of the value box rectangle stroke.*/
    var colorValueBoxRectEnd: dynamic   /*if defined it enables use of gradient on value box rectangle stroke.*/
    var colorValueBoxBackground: dynamic    /*defines background color for value box.*/
    var colorValueBoxShadow: dynamic    /*defines a color of value box shadow. If falsy shadow won’t be drawn.*/
    var colorNeedleShadowUp: dynamic    /*defines upper half of the needle shadow color.*/
    var colorNeedleShadowDown: dynamic  /*defines drop shadow needle color.*/
    var colorBarStroke: dynamic     /*color of a bar stroke.*/
    var colorBar: dynamic           /*a bar background color.*/
    var colorBarProgress: dynamic   /*defines a progress bar color.*/
    var highlightsLineCap: dynamic  /*‘round’, ‘square’ or ‘butt’. Default is ‘butt’. It sets the context.lineCap within the drawRadialHighLights function of the RadialGauge object. Then format option to set the “units” attribute. For example “{value} % {title}” which replaces the attributes inside {} to the same member in the option object. So if title set to “Hour” and value to “50” the units will be “50% Hour”.*/

    /* Needle Configuration Options */
    var needle: Boolean             /*boolean, specifies if gauge should draw the needle or not.*/
    var needleShadow: Boolean       /*boolean, specifies if needle should drop shadow or not.*/
    var needleType: String          /*string, one of “arrow” or “line” supported.*/
    var needleStart: dynamic        /*tail part of the needle length, in relative units.*/
    var needleEnd: dynamic          /*main needle length in relative units.*/
    var needleWidth: dynamic        /*max width of the needle in the widest needle place.*/

    /* Borders Options */
    var borders: Boolean            /*boolean, defines if borders should be drawn or not.*/
    var borderOuterWidth: dynamic   /*specifies a width in pixels of the outer border. If set to zero - border won’t be drawn at all.*/
    var borderMiddleWidth: dynamic  /*specifies a width in pixels of the middle border. If set to zero - border won’t be drawn at all.*/
    var borderInnerWidth: dynamic   /*specifies a width in pixels of the inner border. If set to zero - border won’t be drawn at all.*/
    var borderShadowWidth: dynamic  /*specifies the width of the outer border drop shadow. If zero - shadow won’t be drawn.*/

    /* Value Box Options */
    var valueBox: Boolean           /*boolean, defines if the value box should be drawn or not on the gauge.*/
    var valueBoxStroke: Number      /*number in relative units which defines the width of stroke of the value box element.*/
    var valueBoxWidth: dynamic      /*if set and is greater than value text real width - will be set as configured. This value is expected to be a percent in relation to gauge width.*/
    var valueText: String           /*text to display instead of showing the current value. It may be useful when it is required to display something different in value box.*/
    var valueTextShadow: dynamic    /*specifies if value text shadow should be drawn or not.*/
    var valueBoxBorderRadius: Number    /*number of radius to draw rounded corners of the value box.*/
    var valueInt: Int               /*integer which defines how many numeric positions should be used to display integer part of the value number.*/
    var valueDec: Int               /*which defines how many positions should be used to display decimal part of the value number.*/

}

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

//external class RadialGauge
