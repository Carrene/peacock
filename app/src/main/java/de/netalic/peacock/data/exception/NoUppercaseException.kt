package de.netalic.peacock.data.exception

class NoUppercaseException : BaseException() {

    override val message: String?
        get() = MESSAGE

    companion object {
        const val MESSAGE = "No uppercase character found in input."
    }

}