package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.App
import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.ModelAuth
import com.fonrouge.rbpiHmi.observableViewType
import io.kvision.core.*
import io.kvision.html.button
import io.kvision.panel.SimplePanel
import io.kvision.panel.flexPanel
import io.kvision.toast.Toast
import io.kvision.toast.ToastMethod
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition
import io.kvision.utils.px
import io.kvision.utils.vmax
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthView : SimplePanel() {

    lateinit var digitPad: DigitPad

    init {
        flexPanel(
            direction = FlexDirection.COLUMN,
            justify = JustifyContent.CENTER
        ) {
            height = 50.vmax
            flexPanel(
                direction = FlexDirection.ROW,
                justify = JustifyContent.CENTER,
                alignItems = AlignItems.CENTER
            ) {
                flexPanel(alignItems = AlignItems.CENTER, className = "shadow") {
                    padding = 20.px
                    border = Border(width = 1.px, style = BorderStyle.SOLID, color = Color("gray"))
                    digitPad = digitPad(
                        type = DigitPad.Type.Password,
                        label = "Enter admin password:"
                    ) {
                        AppScope.launch {
                            authenticate(digitPad.textWidget.value)
                        }
                    }
                    button(text = "Submit").onClick {
                        digitPad.textWidget.value?.let {
                            AppScope.launch {
                                authenticate(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun authenticate(password: String?) {
        password?.let {
            if (ModelAuth.authenticate(password)) {
                delay(250)
                observableViewType.value = App.ViewType.ConfigAuth
                Toast.info(
                    message = "Admin logged",
                    options = ToastOptions(
                        positionClass = ToastPosition.TOPRIGHT,
                        hideMethod = ToastMethod.SLIDEUP
                    )
                )
            } else {
                observableViewType.setState(App.ViewType.Main)
                Toast.error(
                    message = "Authentication failed: incorrect password.",
                    options = ToastOptions(
                        positionClass = ToastPosition.TOPRIGHT,
                        hideMethod = ToastMethod.SLIDEUP
                    )
                )
            }
        }
    }
}
