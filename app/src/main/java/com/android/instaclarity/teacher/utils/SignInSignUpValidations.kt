package com.android.instaclarity.teacher.utils

import android.support.annotation.IntDef

class SignInSignUpValidations {

    companion object {

        const val EMPTY_FULL_NAME = 1
        const val EMPTY_MOBILE_NUMBER = 2
        const val INVALID_MOBILE_NUMBER = 3
        const val INVALID_PASSWORD = 4
        const val EMPTY_PASSWORD = 5
        const val EMAIL_MISMATCH = 6
        const val EMPTY_CONFIRM_EMAIL = 7
        const val INVALID_CONFIRM_EMAIL = 8
        const val EMPTY_EMAIL = 9
        const val INVALID_EMAIL = 10
        const val INVALID_PIN = 11
        const val EMPTY_PIN = 12
    }

    @IntDef(EMPTY_FULL_NAME, EMPTY_MOBILE_NUMBER, INVALID_MOBILE_NUMBER,
            INVALID_PASSWORD, EMPTY_PASSWORD, EMAIL_MISMATCH, EMPTY_CONFIRM_EMAIL, INVALID_CONFIRM_EMAIL, EMPTY_EMAIL,
            INVALID_EMAIL, INVALID_PIN, EMPTY_PIN)
    @Retention(AnnotationRetention.SOURCE)
    annotation class UserValidation
}