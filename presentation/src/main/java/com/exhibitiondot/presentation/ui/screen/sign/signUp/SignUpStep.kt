package com.exhibitiondot.presentation.ui.screen.sign.signUp

sealed class SignUpStep(val title: String, val description: String, val percentage: Float) {
    abstract fun onPrevStep() : SignUpStep?
    abstract fun onNextStep() : SignUpStep?

    data object InfoStep : SignUpStep("회원 정보 입력", "", 0.1f) {
        override fun onPrevStep(): SignUpStep? {
            return null
        }
        override fun onNextStep(): SignUpStep {
            return RegionStep
        }
    }

    data object RegionStep : SignUpStep("", "", 0.4f) {
        override fun onPrevStep(): SignUpStep {
            return InfoStep
        }
        override fun onNextStep(): SignUpStep {
            return CategoryStep
        }
    }

    data object CategoryStep : SignUpStep("", "", 0.7f) {
        override fun onPrevStep(): SignUpStep {
            return RegionStep
        }
        override fun onNextStep(): SignUpStep {
            return EventTypeStep
        }
    }

    data object EventTypeStep : SignUpStep("", "", 1.0f) {
        override fun onPrevStep(): SignUpStep {
            return CategoryStep
        }
        override fun onNextStep(): SignUpStep? {
            return null
        }
    }
}