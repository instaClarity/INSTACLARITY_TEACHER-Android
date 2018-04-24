package com.android.instaclarity.teacher.utils

import android.support.annotation.IntDef

class PasswordValidations {

    companion object {

        const val EMPTY_NEW_PASSWORD = 1
        const val EMPTY_OLD_PASSWORD = 2
        const val EMPTY_CONFIRM_PASSWORD = 3
        const val PASSWORD_MISMATCH = 4
        const val INVALID_NEW_PASSWORD = 5
        const val INVALID_OLD_PASSWORD = 6
        const val INVALID_CONFIRM_PASSWORD = 7
    }

    @IntDef(EMPTY_NEW_PASSWORD, EMPTY_OLD_PASSWORD, EMPTY_CONFIRM_PASSWORD,
            PASSWORD_MISMATCH, INVALID_NEW_PASSWORD, INVALID_OLD_PASSWORD, INVALID_CONFIRM_PASSWORD)
    @Retention(AnnotationRetention.SOURCE)
    annotation class PasswordValidation
}
