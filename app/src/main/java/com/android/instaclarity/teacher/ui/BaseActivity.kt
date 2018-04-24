package com.android.instaclarity.teacher.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.android.instaclarity.teacher.R
import com.android.instaclarity.teacher.ui.loginsignup.login.LoginActivity
import com.android.instaclarity.teacher.utils.AppLogger
import com.android.instaclarity.teacher.utils.SharedPreferenceManager
import com.android.instaclarity.teacher.utils.ToastUtils
import com.android.instaclarity.teacher.widgets.progress.AppProgressLoader

@SuppressLint("Registered")
open
/**
 * The Base class for activities that use the [support library]({@docRoot}tools/extras/support-library.html) features base methods of project. <br></br>
 * <br></br>
 * It is mandatory for every activity to extend from this. Common methods applicable to views are implemented here. Plz add application specific methods here.
 */
class BaseActivity : AppCompatActivity(), BaseView {

    override fun isInternetWorking(): Boolean {
        val netInfo = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (netInfo != null && netInfo.isConnected) {
            return true
        }
        val parentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        if (parentView != null) {
            Snackbar.make(parentView, getString(R.string.check_your_internet_connection), Snackbar.LENGTH_LONG).show()
        } else {
            showMessage(getString(R.string.check_your_internet_connection))
        }
        return false
    }

    override fun logOutFromApp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var appProgressLoader: AppProgressLoader? = null

    private val preferenceManager: SharedPreferenceManager
        get() = SharedPreferenceManager.getInstance(this)

    /**
     * This method is used to show progress loader in full screen.<br></br>
     * It will closed any previously running dialog and then create and show new dialog.<br></br>
     * [AppProgressLoader] is the loader class which is used to show loader.<br></br>
     * <br></br>
     * Please check [.closeProgressDialog],  [AppProgressLoader]
     */
    override fun showProgressDialog() {
        if (appProgressLoader == null || !appProgressLoader!!.isShowing) {
            if (appProgressLoader != null) {
                closeProgressDialog()
            }
            appProgressLoader = AppProgressLoader(this)
            appProgressLoader!!.show()
            if (appProgressLoader!!.window != null) {
                appProgressLoader!!.window!!.setGravity(Gravity.CENTER)
            }
        }
    }

    /**
     * Called when user private token get expired
     */
    override fun onTokenExpired() {

//        preferenceManager.setString(Constants.KEY_PRIVATE_ACCESS_TOKEN, "")
//        preferenceManager.setString(Constants.KEY_USER_NAME, "")
//        preferenceManager.setString(Constants.KEY_USER_EMAIL, "")
//        preferenceManager.setString(Constants.KEY_USER_NUMBER, "")
//        preferenceManager.setString(Constants.KEY_USER_PIN, "")
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    /**
     * This method is used to close progress loader that are running on screen.<br></br>
     * [AppProgressLoader] is the loader class which is used to show loader. <br></br>
     * <br></br>Please check [.closeProgressDialog],  [AppProgressLoader]
     */
    override fun closeProgressDialog() {
        try {
            if (appProgressLoader != null && appProgressLoader!!.isShowing) {
                appProgressLoader!!.dismiss()
                appProgressLoader = null
            }
        } catch (e: Exception) {
            AppLogger.e(e)
        }
    }

    override fun showLongToastMessage(msg: String) {
        ToastUtils().showLongToast(this, msg)
    }

    override fun showMessage(msg: String) {
        ToastUtils().showToast(this, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (actionBar != null)
            actionBar!!.hide()
        if (supportActionBar != null)
            supportActionBar!!.hide()
//        val window = this.window
//        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

    }

    override fun showNoInternetConnectionDialog() {
        val parentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        if (parentView != null) {
            Snackbar.make(parentView, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG).show()
        } else {
            showMessage(getString(R.string.no_internet_connection))
        }
    }
    override fun showSnackBar(string: Int) {
        val parentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        if (parentView != null) {
            Snackbar.make(parentView, getString(string), Snackbar.LENGTH_LONG).show()
        } else {
            showMessage(getString(R.string.no_internet_connection))
        }
    }

    override fun showSnackBar(string: String) {
        val parentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        if (parentView != null) {
            Snackbar.make(parentView, string, Snackbar.LENGTH_LONG).show()
        } else {
            showMessage(getString(R.string.no_internet_connection))
        }
    }

    /**
     * remove/hide keyboard from screen with unchanged focused element/view
     *
     * @param view currently focused EditText.
     */
    override fun hideKeyBoard(view: View?) {
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        AppLogger.d("Permissions count " + permissions.size + "  " + requestCode + requestCode)
        //This method is in use. Do not remove it.
    }

}