package de.netalic.peacock.util

import android.content.Context
import kotlin.math.roundToInt

object Converter {

    fun dpToPx(dp: Int,context: Context): Int {
        val density = context.resources
            .displayMetrics
            .density
        return (dp.toFloat() / density).roundToInt()
    }
}