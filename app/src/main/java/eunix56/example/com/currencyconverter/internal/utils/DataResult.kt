package eunix56.example.com.currencyconverter.internal.utils

data class DataResult<out T> (val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): DataResult<T> {
            return DataResult(Status.SUCCESS, data)
        }

        fun <T> loading(): DataResult<T> {
            return DataResult(Status.LOADING)
        }

        fun <T> error(message: String, data: T? = null): DataResult<T> {
            return DataResult(Status.ERROR, data, message)
        }
    }
}