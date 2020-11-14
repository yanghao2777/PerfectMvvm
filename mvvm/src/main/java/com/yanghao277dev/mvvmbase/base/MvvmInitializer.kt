package com.yanghao277dev.mvvmbase.base

import android.content.Context
import androidx.startup.Initializer
import com.yanghao277dev.mvvmbase.http.HttpHelper

class MvvmInitializer : Initializer<Unit> {
    private val TAG = "BaseInitializer"

    override fun create(context: Context) {
        MyLog.debugLog("####create ",this::class.java)


    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}