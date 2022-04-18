package com.fonrouge.rbpiHmi

import io.kvision.core.AlignItems
import io.kvision.form.text.Password
import io.kvision.form.text.password
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.panel.hPanel

class LoginForm : SimplePanel() {

    val colW = 4

    lateinit var password: Password

    init {
        var i = 0

        div(className = "container shadow") {
            div(className = "row") {
                div(className = "col-4 d-flex align-items-center") {
                    hPanel(alignItems = AlignItems.BASELINE/*,  justify = JustifyContent.SPACEBETWEEN*/) {
                        label(content = "Password:")
                        password = password(value = "")
                        button(text = "X", style = ButtonStyle.OUTLINEDARK).onClick {
                            password.value = ""
                        }
                    }
                }
                div(className = "col-4") {
                    span(content = "...")
                }
                div(className = "col-4") {
                    div(className = "container") {
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
    }

    private fun digitPressed(i: Int) {
        if (password.value == null) {
            password.value = i.toString()
        } else {
            password.value += i
        }
        console.warn("digit pressed:", i, "pwd", password.value)
    }
}
