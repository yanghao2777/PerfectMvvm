package com.yanghao277dev.mvvmbase.http

import com.yanghao277dev.mvvmbase.base.MyLog.netState
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        netState(TAG, message)
    }

    companion object {
        private val TAG = HttpLogger::class.java.simpleName
    }
}