package com.example.mvvmexample

import android.app.Application
import com.yanghao277dev.mvvmbase.http.HttpHelper

class MvvmExampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpHelper.Builder(this)
            .initOkHttp()
            .createRetrofit(ApiService.BASE_URL)
            .build()
    }
}