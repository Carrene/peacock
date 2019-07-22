package de.netalic.peacock.util

import android.content.Context
import android.provider.Settings
import android.util.Log
import java.security.MessageDigest

object PhoneInfo {
    private const val SHA_1 = "SHA-1"
    const val HEXCHARS = "0123456789ABCDEF"

    fun getPhoneUdid(context: Context): String {
        val phoneId = Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        val udid = hashString(SHA_1, phoneId)
        Log.v("udid:", udid)
        //
        return udid
    }

    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest.getInstance(type).digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEXCHARS[i shr 4 and 0x0f])
            result.append(HEXCHARS[i and 0x0f])
        }

        return result.toString()
    }

}