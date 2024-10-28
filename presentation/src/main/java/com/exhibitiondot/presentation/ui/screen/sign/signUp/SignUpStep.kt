package com.exhibitiondot.presentation.ui.screen.sign.signUp

import androidx.annotation.StringRes
import com.exhibitiondot.presentation.R

sealed class SignUpStep(
    @StringRes val description: Int,
    val percentage: Float,
) {
    abstract fun onPrevStep() : SignUpStep?
    abstract fun onNextStep() : SignUpStep?

    data object InfoStep : SignUpStep(R.string.signup_info_step_description, 0.1f) {
        override fun onPrevStep(): SignUpStep? {
            return null
        }
        override fun onNextStep(): SignUpStep {
            return RegionStep
        }
    }

    data object RegionStep : SignUpStep(R.string.signup_region_step_description, 0.4f) {
        override fun onPrevStep(): SignUpStep {
            return InfoStep
        }
        override fun onNextStep(): SignUpStep {
            return CategoryStep
        }
    }

    data object CategoryStep : SignUpStep(R.string.signup_category_step_description, 0.7f) {
        override fun onPrevStep(): SignUpStep {
            return RegionStep
        }
        override fun onNextStep(): SignUpStep {
            return EventTypeStep
        }
    }

    data object EventTypeStep : SignUpStep(R.string.signup_event_type_step_description, 1.0f) {
        override fun onPrevStep(): SignUpStep {
            return CategoryStep
        }
        override fun onNextStep(): SignUpStep? {
            return null
        }
    }
}