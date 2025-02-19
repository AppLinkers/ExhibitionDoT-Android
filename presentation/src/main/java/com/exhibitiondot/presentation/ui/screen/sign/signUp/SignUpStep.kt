package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.annotation.StringRes
import com.exhibitiondot.presentation.R

enum class SignUpStep(
    @StringRes val description: Int,
    val percentage: Float,
) {
    InfoStep(R.string.signup_info_step_description, 0.1f),
    RegionStep(R.string.signup_region_step_description, 0.4f),
    CategoryStep(R.string.signup_category_step_description, 0.7f),
    EventTypeStep(R.string.signup_event_type_step_description, 1.0f),
}