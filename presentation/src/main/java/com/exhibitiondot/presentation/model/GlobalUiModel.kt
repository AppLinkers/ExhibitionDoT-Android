package com.exhibitiondot.presentation.model

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlobalUiModel @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}