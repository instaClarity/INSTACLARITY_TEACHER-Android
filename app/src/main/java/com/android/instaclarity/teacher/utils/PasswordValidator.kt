package com.android.instaclarity.teacher.utils

import java.util.regex.Matcher
import java.util.regex.Pattern


class PasswordValidator {


    private var pattern1: Pattern? = null
    private var pattern2: Pattern? = null
    private var pattern3: Pattern? = null
    private var pattern4: Pattern? = null
    private var matcher1: Matcher? = null
    private var matcher2: Matcher? = null
    private var matcher3: Matcher? = null
    private var matcher4: Matcher? = null
    private val passwordPattern1 = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%&+\\_\\-.*!~,?])(?=\\S+$).{8,}$"
    private val passwordPattern2 = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%&+\\_\\-.*!~,?])(?=\\S+$).{8,}$"
    private val passwordPattern3 = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%&+\\_\\-.*!~,?])(?=\\S+$).{8,}$"
    private val passwordPattern4 = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$"

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    fun validate(password: String): Boolean {

        matcher1 = pattern1?.matcher(password)
        matcher2 =pattern2?.matcher(password)
        matcher3 =pattern3?.matcher(password)
        matcher4 =pattern4?.matcher(password)
        return matcher1!!.matches()||matcher2!!.matches()||matcher3!!.matches()||matcher4!!.matches()

    }

    init {
        pattern1 = Pattern.compile(passwordPattern1)
        pattern2 = Pattern.compile(passwordPattern2)
        pattern3 = Pattern.compile(passwordPattern3)
        pattern4 = Pattern.compile(passwordPattern4)
    }
}
