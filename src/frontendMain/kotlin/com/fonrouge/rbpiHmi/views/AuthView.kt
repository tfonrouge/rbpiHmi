package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.App
import com.fonrouge.rbpiHmi.AppScope
import com.fonrouge.rbpiHmi.Model
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
            if (Model.authenticate(password)) {
                delay(1000)
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

    /*
                                AuthView().getResult()?.let {
                                    if (it) {
                                        AppConfigView().getResult()?.let {
                                            if (it) {
                                                Toast.info(
                                                    message = "Saved preferences ok",
                                                    options = ToastOptions(
                                                        positionClass = ToastPosition.TOPRIGHT,
                                                        hideMethod = ToastMethod.SLIDEUP
                                                    )
                                                )
                                            } else {
                                                Toast.warning(
                                                    message = "Error on saving preferences",
                                                    options = ToastOptions(
                                                        positionClass = ToastPosition.TOPRIGHT,
                                                        hideMethod = ToastMethod.SLIDEUP
                                                    )
                                                )
                                            }
                                        } ?: Toast.warning(
                                            message = "Cancelled",
                                            options = ToastOptions(
                                                positionClass = ToastPosition.TOPRIGHT,
                                                hideMethod = ToastMethod.SLIDEUP
                                            )
                                        )
                                        observableViewType.setState(ViewType.Main)
                                    } else {
                                        observableViewType.setState(ViewType.Main)
                                        Toast.error(
                                            message = "Authentication failed: incorrect password.",
                                            options = ToastOptions(
                                                positionClass = ToastPosition.TOPRIGHT,
                                                hideMethod = ToastMethod.SLIDEUP
                                            )
                                        )
                                    }
                                } ?: kotlin.run {
                                    observableViewType.setState(ViewType.Main)
                                    Toast.warning(
                                        message = "Authentication cancelled.",
                                        options = ToastOptions(
                                            positionClass = ToastPosition.TOPRIGHT,
                                            hideMethod = ToastMethod.SLIDEUP
                                        )
                                    )
                                }
*/

}
