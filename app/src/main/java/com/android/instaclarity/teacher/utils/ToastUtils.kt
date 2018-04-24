package com.android.instaclarity.teacher.utils

import android.content.Context
import android.widget.Toast

/**
 * This is common class to show default toast message on UI.
 */
class ToastUtils {

    /**
     * Show toast on UI with string message.
     *
     * @param message the message
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Show toast on UI from string resource.
     *
     * @param messageId the message id
     */
    fun showToast(context: Context, messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }
}
