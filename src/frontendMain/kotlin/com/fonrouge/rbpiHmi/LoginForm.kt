package com.fonrouge.rbpiHmi

import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.panel.hPanel

class LoginForm : SimplePanel() {

    val colW = 4

    init {
        div(className = "container") {

            var i = 0

            hPanel {
                div {
                    text(label = "Password:")
                }
                div {
                    for (x in 1..3) {
                        div(className = "row") {
                            for (y in 1..3) {
                                ++i
                                div(className = "col-$colW") {
                                    val n = i
                                    button(text = i.toString(), className = "btn-digit").onClick {
                                        digitPressed(n)
                                    }
                                }
                            }
                        }
                    }
                    div(className = "row") {
                        div(className = "col-$colW") {
                            button(text = "0", className = "btn-digit").onClick { digitPressed(0) }
                        }
                        div(className = "col-${colW * 2}") {
                            button(text = "ENTER", className = "btn-digit").onClick {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun digitPressed(i: Int) {
        console.warn("digit pressed:", i)
    }
}
