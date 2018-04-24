package com.android.instaclarity.teacher.ui.loginsignup.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.instaclarity.teacher.R
import com.android.instaclarity.teacher.ui.BaseActivity
import com.android.instaclarity.teacher.ui.home.HomeActivity
import com.android.instaclarity.teacher.ui.loginsignup.signup.SignUpActivity
import com.android.instaclarity.teacher.utils.SignInSignUpValidations
import com.android.instaclarity.teacher.widgets.AppEditText
import io.swagger.client.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.View, View.OnClickListener {

    private lateinit var etUsername: AppEditText
    private lateinit var etPassword: AppEditText
    private lateinit var mPresenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        etUsername = findViewById(R.id.activity_login_et_username)
        etPassword = findViewById(R.id.activity_login_et_password)


        activity_login_tv_sign_in.setOnClickListener(this)
        activity_login_tv_sign_up.setOnClickListener(this)
        mPresenter = LoginPresenter(this, this)
        mPresenter.getToken()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.activity_login_tv_sign_in -> mPresenter.login(getUserData())

            R.id.activity_login_tv_sign_up -> {
                startActivity(Intent(this, SignUpActivity::class.java))
//                this.overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left)
            }
        }
    }

    private fun getUserData(): User{
        val user = User()

        user.email = etUsername.text.toString().trim()
        user.password = etPassword.text.toString().trim()

        return user
    }

    /**
     * Move to home screen
     */
    override fun moveToHomeScreen() {
        startActivity(Intent(this,HomeActivity::class.java))
        finish()
    }

    override fun onError(validationCheck: Int) {
        when (validationCheck) {
            SignInSignUpValidations.EMPTY_EMAIL ->  {
                etUsername.requestFocus()
                etUsername.error = getString(R.string.empty_email_error)
            }

            SignInSignUpValidations.INVALID_EMAIL -> {
                etUsername.requestFocus()
                etUsername.error = getString(R.string.email_format_error)
            }

            SignInSignUpValidations.EMPTY_PASSWORD -> {
                etPassword.requestFocus()
                etPassword.error = getString(R.string.empty_password_error)
            }

            SignInSignUpValidations.INVALID_PASSWORD -> {
                etPassword.requestFocus()
                etPassword.error = getString(R.string.password_error)
            }
        }
    }
}
