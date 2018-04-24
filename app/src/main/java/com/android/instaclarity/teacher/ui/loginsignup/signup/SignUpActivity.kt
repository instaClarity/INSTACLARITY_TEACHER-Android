package com.android.instaclarity.teacher.ui.loginsignup.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import com.android.instaclarity.teacher.R
import com.android.instaclarity.teacher.ui.BaseActivity
import com.android.instaclarity.teacher.ui.home.HomeActivity
import com.android.instaclarity.teacher.widgets.AppEditText
import io.swagger.client.model.Teacher
import io.swagger.client.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(), SignUpContract.View, View.OnClickListener {

    private lateinit var presenter: SignUpContract.Presenter
    private lateinit var etFullName: AppEditText
    private lateinit var etEmailAddress: AppEditText
    private lateinit var etContactNumber: AppEditText
    private lateinit var etHighestQualification: AppEditText
//    private lateinit var spnClassToTeach: Spinner
    private lateinit var spnClassToTeach: AppEditText
    private lateinit var etYearsOfExperience: AppEditText
//    private lateinit var spnSpecialization: Spinner
    private lateinit var spnSpecialization: AppEditText
    private lateinit var etYourLocation: AppEditText
    private lateinit var etPassword: AppEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etFullName = findViewById(R.id.activity_sign_up_et_full_name)
        etEmailAddress = findViewById(R.id.activity_sign_up_et_email_address)
        etContactNumber = findViewById(R.id.activity_sign_up_et_contact_number)
        etHighestQualification = findViewById(R.id.activity_sign_up_et_highest_qualification)
        spnClassToTeach = findViewById(R.id.activity_sign_up_et_class_to_teach)
        etYearsOfExperience = findViewById(R.id.activity_sign_up_et_years_of_experience)
        spnSpecialization = findViewById(R.id.activity_sign_up_et_specialization)
        etYourLocation = findViewById(R.id.activity_sign_up_et_your_location)
        etPassword = findViewById(R.id.activity_sign_up_et_password)

        presenter = SignUpPresenter(this,this)

        activity_sign_up_btn_sign_up.setOnClickListener(this)
        activity_sign_up_iv_back_icon.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.activity_sign_up_btn_sign_up){
            presenter.signUp(getUserData())

        } else if(v?.id == R.id.activity_sign_up_iv_back_icon){
            onBackPressed()
        }
    }

    private fun getUserData(): User {
        val user = User()

        user.fullName = etFullName.text.toString().trim()
        user.email = etEmailAddress.text.toString().trim()
        user.mobileNumber = etContactNumber.text.toString().trim()
        user.studyClass = spnClassToTeach.text.toString().trim().toInt()

        val teacher = Teacher()

        teacher.experience = etYearsOfExperience.text.toString().trim().toInt()
        teacher.specialization = spnSpecialization.text.toString().trim()
        teacher.location = etYourLocation.text.toString().trim()

        user.teacher = teacher
        user.password = etPassword.text.toString().trim()

        return user
    }

    override fun moveToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onError(validationCheck: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}