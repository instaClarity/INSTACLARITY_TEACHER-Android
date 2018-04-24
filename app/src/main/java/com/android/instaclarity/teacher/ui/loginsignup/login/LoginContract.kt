package com.android.instaclarity.teacher.ui.loginsignup.login

import com.android.instaclarity.teacher.ui.BaseView
import com.android.instaclarity.teacher.utils.SignInSignUpValidations
import io.swagger.client.model.User

interface LoginContract {

    interface View: BaseView{

        fun moveToHomeScreen()

        fun onError(@SignInSignUpValidations.UserValidation validationCheck: Int)
    }

    interface Presenter{

        fun getToken()

        fun login(user: User)
    }
}