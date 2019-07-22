package de.netalic.peacock.util

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt


object Converter {

    fun dpToPx(dp: Float,context: Context): Int {
        val density = context.resources
            .displayMetrics
            .density
        return (dp * density / 160).roundToInt()


    }

//    fun dpToPx(dp: Float, context: Context): Int {
//        return TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            dp,
//            context.resources.displayMetrics
//        ).roundToInt()
//
//    }
}