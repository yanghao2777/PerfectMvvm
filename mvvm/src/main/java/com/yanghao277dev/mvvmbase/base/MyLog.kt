package com.yanghao277dev.mvvmbase.base

import android.os.Looper
import com.orhanobut.logger.*
import android.os.Process

object MyLog {
    private const val LOG_NET = false

    init { onCreate() }

    fun <T> lifeStateA(lifeState: String, c : Class<T>) {
        Logger.v("###ActivityLifeState(class:${c.name}):$lifeState")
    }

    fun <T> lifeStateF(lifeState: String, c : Class<T>) {
        Logger.v("###FragmentLifeState(class:${c.name}):$lifeState")
    }

    fun <T> debugLog(msg: String,c : Class<T>) {
        Logger.e("###Debug(package:${c.`package`?.name ?: "unknown"}):$msg")
    }

    fun  debugLog(msg: String) {
        Logger.e("###Debug:$msg")
    }

    fun <T> runningLog(msg: String,c : Class<T>){
        Logger.d("###Running(package:${c.`package`?.name ?: "unknown"}):$msg")
    }

    fun threadLog(tag : String){
        Logger.e(tag + "#######threadId:" + Thread.currentThread().id.toString() +
                "\n#####processTid:" + Process.myTid().toString() +
                "\n###isMainThread:" + (if(Looper.getMainLooper() == Looper.myLooper()) "true" else "false"))
    }

    fun netState(TAG: String, requestState: String) {
        if(LOG_NET) Logger.i("###Network(TAG:$TAG):$requestState")
    }

    private fun onCreate() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true).tag("###").methodCount(2).build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return !BuildConfig.DEBUG
            }
        })
    }
}