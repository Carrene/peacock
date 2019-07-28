package de.netalic.peacock.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

//TODO-tina companion object
object CommonUtils {

    fun hideSoftKeyboard(activity: FragmentActivity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}