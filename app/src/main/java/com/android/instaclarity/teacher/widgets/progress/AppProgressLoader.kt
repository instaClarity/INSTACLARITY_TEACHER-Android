package com.android.instaclarity.teacher.widgets.progress

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams
import com.android.instaclarity.teacher.R

/**
 * This class is used as a full screen custom progress bar for application<br></br>
 * It can be used anywhere by calling its constructor and show method.
 */
class AppProgressLoader
/**
 * Instantiates a new App progress loader.
 *
 * @param context the context
 */
(context: Context) : Dialog(context, R.style.AppProgressLoader) {

    /**
     * This method is used to show full screen dialog in application with center app icon.
     */
    override fun show() {
        this.setTitle("")
        this.setCancelable(false)
        this.setOnCancelListener(null)

        // here we pass null because here we don't have parent view group
        val view = layoutInflater.inflate(R.layout.app_progress_loader, null)

        // The next line will add the ProgressBar to the dialog.
        val progressWheel = view.findViewById<View>(android.R.id.progress) as ProgressWheel
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        this.addContentView(view, layoutParams)

        progressWheel.spin()
        super.show()
    }
}
