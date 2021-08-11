package eunix56.example.com.currencyconverter.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import eunix56.example.com.currencyconverter.internal.utils.DataResult
import kotlinx.coroutines.Dispatchers

fun <T, A> dataLiveData(databaseQuery: () -> LiveData<T>,
                          networkCall: suspend() -> DataResult<A>,
                          saveCallResult: suspend(A) -> Unit): LiveData<DataResult<T>> =
    liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        val source = databaseQuery.invoke().map { DataResult.success(it) }

        if (source.value?.data != null)
            emitSource(source)

        val responseStatus = networkCall.invoke()

        if (responseStatus.status == DataResult.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            val source = databaseQuery.invoke().map { DataResult.success(it) }
            emitSource(source)
        } else if (responseStatus.status == DataResult.Status.ERROR) {
            emit(DataResult.error(responseStatus.message!!))
            emitSource(source)
        }
    }