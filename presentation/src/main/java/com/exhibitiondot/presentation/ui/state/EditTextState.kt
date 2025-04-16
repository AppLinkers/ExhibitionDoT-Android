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

open class EditTextState(
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

class PhoneEditTextState : EditTextState("", 11) {
    override fun isValidate(): Boolean {
        return regex.matches(trimmedText())
    }

    companion object {
        private val regex = Regex("^\\d{3}\\d{3,4}\\d{4}$")
    }
}

class EmailEditTextState : EditTextState("", 64) {
    override fun isValidate(): Boolean {
        return regex.matches(trimmedText())
    }

    companion object {
        private val regex = Regex("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$")
    }
}