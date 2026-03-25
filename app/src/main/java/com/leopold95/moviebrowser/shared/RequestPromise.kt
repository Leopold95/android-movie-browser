package com.leopold95.moviebrowser.shared

data class RequestPromise<T>(val success: Boolean, val data: T?, val error: String?){
    companion object{
        fun<T> ok(data: T?): RequestPromise<T>{
            return RequestPromise(true, data, null)
        }

        fun<T> error(message: String?): RequestPromise<T>{
            return RequestPromise(false, null, message)
        }
    }
}