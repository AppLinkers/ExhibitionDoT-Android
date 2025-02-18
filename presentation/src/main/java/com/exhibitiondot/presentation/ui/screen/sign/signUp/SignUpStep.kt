package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.annotation.StringRes
import com.exhibitiondot.presentation.R

/*sealed class SignUpStep(
    @StringRes val description: Int,
    val percentage: Float,
) {
    abstract fun prevStep() : SignUpStep?
    abstract fun nextStep() : SignUpStep?

    data object InfoStep : SignUpStep(R.string.signup_info_step_description, 0.1f) {
        override fun prevStep(): SignUpStep? = null
        override fun nextStep(): SignUpStep = RegionStep
    }

    data object RegionStep : SignUpStep(R.string.signup_region_step_description, 0.4f) {
        override fun prevStep(): SignUpStep = InfoStep
        override fun nextStep(): SignUpStep = CategoryStep
    }

    data object CategoryStep : SignUpStep(R.string.signup_category_step_description, 0.7f) {
        override fun prevStep(): SignUpStep = RegionStep
        override fun nextStep(): SignUpStep = EventTypeStep
    }

    data object EventTypeStep : SignUpStep(R.string.signup_event_type_step_description, 1.0f) {
        override fun prevStep(): SignUpStep = CategoryStep
        override fun nextStep(): SignUpStep? = null
    }
}*/

enum class SignUpStep(
    @StringRes val description: Int,
    val percentage: Float,
) {
    InfoStep(R.string.signup_info_step_description, 0.1f),
    RegionStep(R.string.signup_region_step_description, 0.4f),
    CategoryStep(R.string.signup_category_step_description, 0.7f),
    EventTypeStep(R.string.signup_event_type_step_description, 1.0f),
}