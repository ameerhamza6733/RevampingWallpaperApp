package com.ameerhamza.animatedgiflivewallpapers.comman.data

sealed class MyResult<out T> {
    data class Success<T>(val data: T) : MyResult<T>()
    data class Error(val message: String) : MyResult<Nothing>()
    data class Loading(val message: String) : MyResult<Nothing>()
}
