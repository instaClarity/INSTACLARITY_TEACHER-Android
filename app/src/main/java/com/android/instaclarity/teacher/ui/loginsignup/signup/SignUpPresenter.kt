package com.android.instaclarity.teacher.ui.loginsignup.signup

import android.content.Context
import com.android.instaclarity.teacher.AppTokenKeys
import com.android.instaclarity.teacher.ui.BasePresenter
import com.android.instaclarity.teacher.utils.AppStringUtil
import com.android.instaclarity.teacher.utils.SignInSignUpValidations
import com.android.instaclarity.teacher.utils.SharedPreferenceManager
import com.android.volley.Response
import io.swagger.client.BaseModel
import io.swagger.client.api.TokenApi
import io.swagger.client.api.UserApi
import io.swagger.client.model.Token
import io.swagger.client.model.User

class SignUpPresenter(context: Context, private val mView: SignUpContract.View) : BasePresenter(context, mView), SignUpContract.Presenter, Response.Listener<BaseModel<Token>> {

    override fun signUp(user: User) {
        if(mView.isInternetWorking()){
//            if(validateUser(user)){
                mView.showProgressDialog()
                UserApi().signUp(mSharedPreferenceManager.getString(SharedPreferenceManager.KEY_PUBLIC_ACCESS_TOKEN,"")
                        , user
                        , Response.Listener<BaseModel<User>> { response ->

                    mView.closeProgressDialog()
                    if (response.status == 200 && response.response!=null){
                        mView.moveToHomeScreen()
                    }else if(response.status == 111 && !response.message.isNullOrEmpty()){
                        mView.showMessage(response.message)
                    }
                }
                        , this
                )
//            }

        }else{
            mView.showNoInternetConnectionDialog()
        }

    }

    override fun getToken() {
        mView.showProgressDialog()
        TokenApi().getToken(AppTokenKeys.CLIENT_ID, AppTokenKeys.CLIENT_SECRET, AppTokenKeys.USER_NAME, AppTokenKeys.USER_PWD,
                AppTokenKeys.GRANT_TYPE, this, this)
    }

    private fun validateUser(user: User): Boolean {
        if (user.email.isEmpty()) {
            mView.onError(SignInSignUpValidations.EMPTY_EMAIL)
            return false
        }else if (!AppStringUtil().isValidEmailAddress(user.email)) {
            mView.onError(SignInSignUpValidations.INVALID_EMAIL)
            return false
        } else if (user.password.isEmpty()) {
            mView.onError(SignInSignUpValidations.EMPTY_PASSWORD)
            return false
        }else if (user.password.length<8) {
            mView.onError(SignInSignUpValidations.INVALID_PASSWORD)
            return false
        }
        return true
    }

    override fun onResponse(response: BaseModel<Token>?) {
        if(response?.status == 200 && response.response != null){
            mView.closeProgressDialog()
            mSharedPreferenceManager.setString(SharedPreferenceManager.KEY_PUBLIC_ACCESS_TOKEN, String.format("%s %s",response.response.tokenType, response.response.accessToken))
            mSharedPreferenceManager.setString(SharedPreferenceManager.KEY_REFRESH_TOKEN, String.format("%s %s",response.response.tokenType, response.response.refreshToken))
        }else{
            mView.closeProgressDialog()
        }
    }

}