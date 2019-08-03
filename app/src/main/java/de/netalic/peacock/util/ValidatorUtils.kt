package de.netalic.peacock.util

class ValidatorUtils {

    companion object {
        //ToDo-tina min and max size of phone number
        private val mPhonePatternMarcher = "[+0-9 ]{5,15}".toRegex()

        fun phoneValidator(phoneNumber: String): Boolean {
            return mPhonePatternMarcher.matches(phoneNumber)
        }
    }
}