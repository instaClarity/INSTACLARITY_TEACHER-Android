package com.android.instaclarity.teacher.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import com.android.instaclarity.teacher.InstaClarityTeacherApplication

/**
 * This class is used as a util class to perform all strings action.
 */
class AppStringUtil {

     fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }
    /**
     * This method is used to check is string is a valid email or not.
     *
     * @param email String email
     * @return boolean that email is valid or not
     */
    fun isValidEmailAddress(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    /**
     * To get spannable string  with associated click actions ,expected fonts and colors
     *
     * @param context               : context
     * @param string                : complete string
     * @param substringList         : first sub string
     * @param fontPosition          : font position to be applied
     * @param color                 : font color
     * @param firstClickableAction  : clickable action for first sub string
     * @param secondClickableAction : clickable action for second sub string
     * @return spannable string  with associated click actions ,expected fonts and colors
     */
    fun getFormattedText(context: Context, string: String, substringList: Array<String>?, fontPosition: Int, color: Int, firstClickableAction: ClickableSpan?, secondClickableAction: ClickableSpan?): SpannableString {
        var substringIndex = -1
        var secondSubStringIndex = -1
        var thirdSubStringIndex = -1
        val spannableString = SpannableString(string)
        if (substringList != null && substringList.isNotEmpty()) {
            substringIndex = string.indexOf(substringList[0])
            if (substringIndex != -1) {
                getSpannableResult(context, substringList[0], fontPosition, color, firstClickableAction, substringIndex, spannableString)
            }
            if (substringList.size > 1) {
                secondSubStringIndex = string.indexOf(substringList[1])
                if (secondSubStringIndex != -1) {
                    getSpannableResult(context, substringList[1], fontPosition, color, secondClickableAction, secondSubStringIndex, spannableString)
                }
            }
            if (substringList.size > 2) {
                thirdSubStringIndex = string.indexOf(substringList[2])
                if (thirdSubStringIndex != -1) {
                    getSpannableResult(context, substringList[2], fontPosition, color, null, thirdSubStringIndex, spannableString)
                }
            }
        }
        return spannableString
    }


    /**
     * To get spannable string  with associated click actions ,expected fonts and colors
     *
     * @param context         : context
     * @param subString       : sub string
     * @param fontPosition    : font position to be applied
     * @param color           : font color
     * @param clickableAction : clickable action for first sub string
     * @return spannable string  with associated click actions ,expected fonts and colors
     */
    private fun getSpannableResult(context: Context?, subString: String, fontPosition: Int, color: Int, clickableAction: ClickableSpan?, substringIndex: Int, spannableString: SpannableString) {
        if (context == null)
            return
        spannableString.setSpan(AppTypeFaceSpan("", (context.applicationContext as InstaClarityTeacherApplication).getFont(fontPosition)!!), substringIndex, substringIndex + subString.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        if (clickableAction != null) {
            spannableString.setSpan(clickableAction, substringIndex, substringIndex + subString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), substringIndex, substringIndex + subString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }


}
