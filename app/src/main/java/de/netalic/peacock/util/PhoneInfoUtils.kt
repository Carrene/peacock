package de.netalic.peacock.util

import android.content.Context
import android.provider.Settings
import java.security.MessageDigest


class PhoneInfoUtils {

    companion object {
        private const val sSHA_1 = "SHA-1"
        private const val sHEX_CHARS = "0123456789ABCDEF"

        fun getPhoneUdid(context: Context): String {
            val phoneId = Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

            return hashString(sSHA_1, phoneId)
        }

        private fun hashString(type: String, input: String): String {
            val bytes = MessageDigest.getInstance(type).digest(input.toByteArray())
            val result = StringBuilder(bytes.size * 2)

            bytes.forEach {
                val i = it.toInt()
                result.append(sHEX_CHARS[i shr 4 and 0x0f])
                result.append(sHEX_CHARS[i and 0x0f])
            }

            return result.toString()
        }
    }

}