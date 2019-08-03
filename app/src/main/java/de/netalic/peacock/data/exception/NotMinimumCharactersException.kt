package de.netalic.peacock.data.exception

class NotMinimumCharactersException : BaseException() {

    override val message: String?
        get() = MESSAGE

    companion object {
        const val MESSAGE = "Length of input is less than desired."
    }

}