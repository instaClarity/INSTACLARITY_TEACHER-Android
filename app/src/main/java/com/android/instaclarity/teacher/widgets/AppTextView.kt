package com.android.instaclarity.teacher.widgets

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.android.instaclarity.teacher.InstaClarityTeacherApplication
import com.android.instaclarity.teacher.R

/**
 * Custom TextView class for displaying custom fonts
 */
class AppTextView : AppCompatTextView {

    /**
     * Instantiates a new Custom TextView.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    /**
     * Instantiates a new Custom TextView.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    /**
     * Instantiates a new Custom TextView.
     *
     * @param context the context
     */
    constructor(context: Context) : super(context) {
        init(null)
    }

    /**
     * Set type face.
     *
     * @param attrs instance.
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TypeFaceFonts)
            typeface = (context.applicationContext as InstaClarityTeacherApplication).getFont(a.getInt(R.styleable.TypeFaceFonts_fonts, 2))
            a.recycle()
        }
    }
}
