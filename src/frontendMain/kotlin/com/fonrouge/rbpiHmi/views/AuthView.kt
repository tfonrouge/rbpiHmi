package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.Model
import io.kvision.core.AlignItems
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.form.text.Password
import io.kvision.form.text.password
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.modal.Dialog
import io.kvision.modal.ModalSize
import io.kvision.panel.flexPanel
import kotlinx.coroutines.launch

class AuthView : Dialog<Boolean>(
    caption = "Enter admin password:",
    closeButton = true,
    centered = true,
    size = ModalSize.SMALL,
) {

    private lateinit var password: Password

    init {

        console.warn("init on AuthView...")

        var n = 0

        flexPanel(
            direction = FlexDirection.COLUMN,
//            wrap = FlexWrap.WRAP,
//            justify = JustifyContent.SPACEEVENLY,
//            alignItems = AlignItems.STRETCH,
//            alignContent = AlignContent.SPACEEVENLY,
        ) {
            flexPanel(
                direction = FlexDirection.COLUMN,
//                wrap = FlexWrap.WRAP,
//                justify = JustifyContent.CENTER,
//                alignItems = AlignItems.CENTER,
//                alignContent = AlignContent.CENTER,
//                spacing = 3
            ) {
                for (x in 1..3) {
                    flexPanel(
                        direction = FlexDirection.ROW,
//                        wrap = FlexWrap.WRAP,
//                        justify = JustifyContent.CENTER,
//                        alignItems = AlignItems.CENTER,
//                        alignContent = AlignContent.CENTER,
                        spacing = 10
                    ) {
                        for (y in 1..3) {
                            val n1 = ++n
                            button(text = "$n", className = "btn-digit").onClick {
                                digitPressed(n1)
                            }
                        }
                    }
                }
                flexPanel(
                    direction = FlexDirection.ROW,
//                    wrap = FlexWrap.WRAP,
//                    justify = JustifyContent.CENTER,
//                    alignItems = AlignItems.CENTER,
//                    alignContent = AlignContent.CENTER,
                    spacing = 5
                ) {
                    button(text = "0", className = "btn-digit").onClick { digitPressed(0) }
                    button(text = "ENTER", className = "btn-digit", style = ButtonStyle.SECONDARY).onClick {
                        AppScope.launch {
                            password.value?.let {
                                setResult(result = Model.auth(it))
                            }
                        }
                    }
                }
            }
            flexPanel(
                direction = FlexDirection.ROW,
                justify = JustifyContent.SPACEBETWEEN,
                alignItems = AlignItems.FLEXSTART,
//                alignContent = AlignContent.SPACEEVENLY,
            ) {
                password = password(value = "")
                button("X", style = ButtonStyle.DARK).onClick {
                    password.value = ""
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
