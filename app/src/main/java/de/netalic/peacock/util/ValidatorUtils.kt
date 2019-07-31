package de.netalic.peacock.util

class ValidatorUtils {

    companion object {
        //ToDo-tina min and max size of phone number
        private val mPhonePatternMarcher = "[+0-9 ]{5,15}".toRegex()
        //TODO-tina mEmail pattern length
        private val mEmailPatternMatcher = "[a-zA-Z0-9._-]{2,30}+@[a-zA-Z0-9-]{2,20}+\\.[a-zA-Z.]{2,10}".toRegex()

        fun phoneValidator(phoneNumber: String): Boolean {
            return mPhonePatternMarcher.matches(phoneNumber)
        }
        fun emailValidator(email: String): Boolean {
            return mEmailPatternMatcher.matches(email)
        }
    }
}