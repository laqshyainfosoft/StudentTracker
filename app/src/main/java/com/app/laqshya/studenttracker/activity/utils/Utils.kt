package com.app.laqshya.studenttracker.activity.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Patterns
import timber.log.Timber


object Utils {
    @JvmStatic
    fun isValidPhone(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches();
    }

    @JvmStatic
    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty();
    }
    @JvmStatic
    fun isValidEmail(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @JvmStatic
    fun isEmptyField(vararg text: String): Boolean {


        for (i in text) {
            if (TextUtils.isEmpty(text.toString())) {
                Timber.d(text.toString())
                return true;

            }
        }
        return false
    }

    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    @JvmStatic
    fun isEmpty(vararg args: String): Boolean {
        for (st in args) {

            if (TextUtils.isEmpty(st)) {
                return true
            }
        }
        return false


    }


}
