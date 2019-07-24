package de.netalic.peacock.data.model

data class MyResponse<T>(
    val status: Status,
    val data: T? = null,
    val throwable: Throwable? = null
) {

    companion object {

        fun <T> loading(): MyResponse<T> {
            return MyResponse(status = Status.LOADING)
        }

        fun <T> success(data: T): MyResponse<T> {
            return MyResponse(status = Status.SUCCESS, data = data)
        }

        fun <T> failed(throwable: Throwable): MyResponse<T> {
            return MyResponse(status = Status.FAILED, throwable = throwable)
        }
    }
}

enum class Status {

    LOADING,
    SUCCESS,
    FAILED
}