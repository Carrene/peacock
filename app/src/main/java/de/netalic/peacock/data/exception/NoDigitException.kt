package de.netalic.peacock.data.exception

class NoDigitException : BaseException() {

    override val message: String?
        get() = MESSAGE

    companion object {
        const val MESSAGE = "No digit found in input."
    }

}