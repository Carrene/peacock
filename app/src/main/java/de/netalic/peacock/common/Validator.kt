package de.netalic.peacock.common

class Validator {

    fun hasMinimumLength(password: String, length: Int): Boolean {
        return password.length >= length
    }

    fun hasSpecialCharacters(password: String): Boolean {
        val customCharacters = arrayOf("@", "#", "$", "%", "+", "=", "_", "*", "?")
        for (customCharacter in customCharacters) {
            if (password.contains(customCharacter)) {
                return true
            }
        }
        return false
    }

    fun hasCapitalLetter(password: String): Boolean {
        return password != password.toLowerCase()
    }

    fun hasDigit(password: String): Boolean {
        return password.matches(".*\\d+.*".toRegex())
    }

}