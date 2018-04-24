package com.android.instaclarity.teacher

import android.app.Application
import android.graphics.Typeface
import android.os.AsyncTask
import io.swagger.client.ApiInvoker

class InstaClarityTeacherApplication : Application(){

    val FONT_SOURCE_SANS_PRO_BOLD = 1
    val FONT_SOURCE_SANS_PRO_REGULAR = 2
    val FONT_OPEN_SANS_REGULAR = 3
    private var typeFaceSourceSansProBold: Typeface? = null
    private var typeFaceSourceSansProRegular: Typeface? = null
    private var typeFaceOpenSansRegular: Typeface? = null

    override fun onCreate() {
        super.onCreate()
        LoadFonts().execute()
        ApiInvoker.initializeInstance()
    }


    /**
     * @param id fonts mapping key.
     * @return instance of fonts.
     */
    fun getFont(id: Int): Typeface? {

        return when (id) {
            FONT_SOURCE_SANS_PRO_BOLD -> typeFaceSourceSansProBold
            FONT_SOURCE_SANS_PRO_REGULAR -> typeFaceSourceSansProRegular
            FONT_OPEN_SANS_REGULAR -> typeFaceOpenSansRegular
            else -> typeFaceSourceSansProRegular
        }
    }

    private inner class LoadFonts : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            typeFaceSourceSansProBold = Typeface.createFromAsset(assets, "fonts/SourceSansPro-Bold.ttf")
            typeFaceSourceSansProRegular = Typeface.createFromAsset(assets, "fonts/SourceSansPro-Regular.ttf")
            typeFaceOpenSansRegular = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf")
            return null
        }
    }
}