package com.fonrouge.rbpiHmi.views

import com.fonrouge.rbpiHmi.AppScope
import io.kvision.core.*
import io.kvision.form.text.Text
import io.kvision.form.text.password
import io.kvision.form.text.text
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.html.image
import io.kvision.modal.Dialog
import io.kvision.modal.ModalSize
import io.kvision.panel.FlexPanel
import io.kvision.panel.flexPanel
import io.kvision.utils.rem
import kotlinx.coroutines.launch
import org.w3c.dom.CustomEventInit

class DigitPad(
    val type: Type,
    label: String? = null,
    val onSubmit: (() -> Unit)? = null
) : FlexPanel(
    direction = FlexDirection.ROW,
    alignItems = AlignItems.CENTER,
    spacing = 10
) {

    val textWidget: Text

    init {
        textWidget = when (type) {
            Type.Text -> text(label = label)
            Type.Password -> password(label = label)
        }
        textWidget.onEvent {
            keypress = { keyboardEvent ->
                if (keyboardEvent.key == "Enter") {
                    onSubmit?.invoke()
                }
            }
        }
        image(src = "icons8-pin-pad-100.png") {
            width = 3.rem
            height = 3.rem
        }.onClick {
            AppScope.launch {
                popup(label).getResult()?.let {
                    if (it) {
                        onSubmit?.invoke()
                    }
                }
                textWidget.dispatchEvent("\n", CustomEventInit())
            }
        }
    }

    private var text1: Text? = null

    private fun popup(caption: String? = null): Dialog<Boolean> {

        val dialog = Dialog<Boolean>(
            caption = caption,
            size = ModalSize.SMALL
        )

        var n = 0

        dialog.apply {

            flexPanel(
                direction = FlexDirection.COLUMN,
            ) {
                text1 = when (type) {
                    Type.Text -> text()
                    Type.Password -> password()
                }
                text1?.value = textWidget.value
                flexPanel(
                    direction = FlexDirection.COLUMN,
                ) {
                    for (x in 1..3) {
                        flexPanel(
                            direction = FlexDirection.ROW,
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
                        spacing = 5
                    ) {
                        button(text = "CLR", className = "btn-digit", style = ButtonStyle.OUTLINEPRIMARY).onClick {
                            textWidget.value = ""
                            text1?.value = ""
                        }
                        button(text = "0", className = "btn-digit").onClick { digitPressed(0) }
                        button(text = "BAK", className = "btn-digit", style = ButtonStyle.OUTLINEPRIMARY).onClick {
                            textWidget.value?.let {
                                if (it.isNotEmpty()) {
                                    textWidget.value = it.substring(0, it.length - 1)
                                    text1?.value = textWidget.value
                                }
                            }
                        }
                    }
                }
                button(text = "ENTER", className = "btn-digit", style = ButtonStyle.SECONDARY).onClick {
                    setResult(result = true)
                }
            }
        }

        return dialog
    }

    private fun digitPressed(i: Int) {
        if (textWidget.value == null) {
            textWidget.value = i.toString()
        } else {
            textWidget.value += i
        }
        text1?.value = textWidget.value
    }

    enum class Type {
        Text,
        Password
    }
}

fun Container.digitPad(
    type: DigitPad.Type,
    label: String? = null,
    onSubmit: (() -> Unit)? = null
): DigitPad {
    val digitPad = DigitPad(
        type = type,
        label = label,
        onSubmit = onSubmit
    )
    add(digitPad)
    return digitPad
}
