package com.jonnyhsia.storybook.kit

import android.text.TextUtils

import java.util.regex.Pattern

object ValidatorKit {

    fun isMobilePhoneNumber(mobilePhoneHumber: String): Boolean {
        if (TextUtils.isEmpty(mobilePhoneHumber)) {
            return false
        }

        val regexMobile = "^(1)\\d{10}$"

        return Pattern.matches(regexMobile, mobilePhoneHumber)
    }

    fun isValidPassword(password: String): Boolean {
        if (password.checkEmpty(checkBlankSpace = true)) {
            return false
        }
        return password.trim { it <= ' ' }.length in 6..20
    }

    fun checkRegisterSMSVerifyCode(verifyCode: String): Boolean {
        return verifyCode.checkNotEmpty(checkBlankSpace = true) && verifyCode.length == 6
    }
}
