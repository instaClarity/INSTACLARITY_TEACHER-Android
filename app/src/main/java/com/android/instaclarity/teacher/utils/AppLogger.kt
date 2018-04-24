package com.android.instaclarity.teacher.utils

import android.util.Log

/**
 * This class is used for logging.
 */
object AppLogger {
    private val LOGGER_TAG = "instaclarity"

    /**
     * This method is used to log info messages on console.
     *
     * @param message the info message
     */
    fun d(message: String) {
        Log.i(LOGGER_TAG, "" + message)
    }

    /**
     * This method is used to log error messages on console and crashlytics.
     *
     * @param exception the exception
     */
    fun e(exception: Exception?, vararg isSendCrash: Boolean) {
        if (exception != null) {
            if (isSendCrash.isNotEmpty()) {
                return
            }
            Log.e(LOGGER_TAG, LOGGER_TAG + exception.message)
        }
    }
}
