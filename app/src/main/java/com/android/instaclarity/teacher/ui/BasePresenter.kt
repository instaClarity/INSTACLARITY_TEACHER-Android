package com.android.instaclarity.teacher.ui

import android.content.Context
import com.android.instaclarity.teacher.utils.SharedPreferenceManager
import com.android.volley.Response
import com.android.volley.VolleyError

open class BasePresenter(val context: Context, private val mView: BaseView) : Response.ErrorListener {
    /**
     * To get Shared Preferences Instance
     *
     * @return Shared Preferences Instance
     */
    protected val mSharedPreferenceManager: SharedPreferenceManager = SharedPreferenceManager.Companion.getInstance(context)

    override fun onErrorResponse(error: VolleyError) {
        mView.closeProgressDialog()
    }

}


