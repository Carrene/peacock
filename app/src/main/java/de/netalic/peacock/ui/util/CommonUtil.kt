package de.netalic.peacock.ui.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

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