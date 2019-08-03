package de.netalic.peacock.data.exception

class NoPasswordMatchException : BaseException() {

    override val message: String?
        get() = MESSAGE

    companion object {
        const val MESSAGE = "No match found between inputs."
    }

}