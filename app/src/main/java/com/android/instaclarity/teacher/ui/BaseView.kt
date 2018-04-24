package com.android.instaclarity.teacher.ui

import android.view.View

/**
 * Contains all the methods which are overridden by most of the views in app
 */
interface BaseView {
    /**
     * To show progress dialog
     */
    fun showProgressDialog()

    /**
     * To dismiss progress dialog
     */
    fun closeProgressDialog()

    /**
     * To hide keyboard
     */
    fun hideKeyBoard(view: View?)

    /**
     * To show message on Screen
     */
    fun showMessage(msg: String)

    /**
     * To show message on Screen
     */
    fun showLongToastMessage(msg: String)

    fun showSnackBar(string: String)

    fun showSnackBar(string: Int)

    fun onTokenExpired()

    /**
     * To show No Internet Connection Dialog
     */
    fun showNoInternetConnectionDialog()

    /**
     * To check Internet working or not.
     */
    fun isInternetWorking(): Boolean

    /**
     * User will logout from App.
     */
    fun logOutFromApp()

}
