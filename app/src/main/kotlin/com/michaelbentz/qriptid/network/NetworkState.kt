package com.michaelbentz.qriptid.network

sealed class NetworkState<out T> {
    data object Idle : NetworkState<Nothing>()
    data object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val message: String?, val throwable: Throwable) : NetworkState<Nothing>()
}