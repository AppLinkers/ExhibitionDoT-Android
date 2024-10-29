package com.exhibitiondot.presentation.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

interface IEditTextState {
    var typedText: String
    fun typeText(text: String)
    fun resetText()
    fun trimmedText(): String
    fun isValidate(): Boolean
}

class EditTextState(
    initText: String = "",
    private val maxLength: Int
): IEditTextState {
    override var typedText by mutableStateOf(initText)

    override fun typeText(text: String) {
        if (text.length <= maxLength) {
            typedText = text
        }
    }

    override fun resetText() {
        typedText = ""
    }

    override fun trimmedText() = typedText.trim()

    override fun isValidate() = trimmedText().isNotEmpty()
}