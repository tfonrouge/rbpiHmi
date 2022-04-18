package com.fonrouge.rbpiHmi

import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.SimplePanel

class LoginForm : SimplePanel() {

    init {
        div(className = "container") {

            var i = 0

            for (x in 1..3) {
                div(className = "row") {
                    for (y in 1..3) {
                        ++i
                        div(className = "col-4") {
                            val n = i
                            button(text = i.toString(), className = "btn-digit").onClick {
                                digitPressed(n)
                            }
                        }
                    }
                }
            }

            div(className = "row") {
                div(className = "col-4") {
                    button(text = "0", className = "btn-digit").onClick { digitPressed(0) }
                }
                div(className = "col-8") {
                    button(text = "ENTER", className = "btn-digit").onClick {

                    }
                }
            }

/*
                div(className = "row") {
                    div(className = "col-4") {
                        button(text = "1", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-2", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-3", className = "btn-digit")
                    }
                }
                div(className = "row") {
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-4", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-5", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-6", className = "btn-digit")
                    }
                }
                div(className = "row") {
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-7", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-8", className = "btn-digit")
                    }
                    div(className = "col-4") {
                        button(text = "", icon = "fas fa-9", className = "btn-digit")
                    }
                }
*/
        }

    }

    private fun digitPressed(i: Int) {
        console.warn("digit pressed:", i)
    }
}
