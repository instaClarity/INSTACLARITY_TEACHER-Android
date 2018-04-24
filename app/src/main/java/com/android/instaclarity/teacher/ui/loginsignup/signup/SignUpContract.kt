package com.android.instaclarity.teacher.ui.loginsignup.signup

import com.android.instaclarity.teacher.ui.BaseView
import com.android.instaclarity.teacher.utils.SignInSignUpValidations
import io.swagger.client.model.User

interface SignUpContract {

    interface View: BaseView{

        fun moveToHomeScreen()

        fun onError(@SignInSignUpValidations.UserValidation validationCheck: Int)
    }

    interface Presenter{

        fun getToken()

        fun signUp(user: User)
    }
}