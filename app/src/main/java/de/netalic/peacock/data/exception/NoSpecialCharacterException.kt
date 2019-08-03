package de.netalic.peacock.data.exception

class NoSpecialCharacterException : BaseException() {

    override val message: String?
        get() = MESSAGE

    companion object {
        const val MESSAGE = "No special character found in input."
    }

}