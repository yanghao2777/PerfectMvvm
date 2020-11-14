package com.example.mvvmexample

import android.app.Application
import com.yanghao277dev.mvvmbase.http.HttpHelper
import com.yanghao277dev.mvvmbase.http.HttpLogger

class MvvmExampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpHelper.init(this,
            ApiService.BASE_URL,
            30,
            HttpLogger(),
            true)
    }
}