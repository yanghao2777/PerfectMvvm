package com.yanghao277dev.mvvmbase.http;


import com.yanghao277dev.mvvmbase.base.MyLog;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private static String TAG = HttpLogger.class.getSimpleName();

    @Override
    public void log(String message) {
        MyLog.INSTANCE.netState(TAG,message);
    }
}