package com.yanghao277dev.mvvmbase.base

import android.os.Looper
import android.os.Process
import android.util.Log

object MyLog {

    fun <T> lifeStateA(lifeState: String, c : Class<T>) {
        val cName = c.name
        Log.v("###ActivityLife",
            "state(class:${cName.substring(cName.lastIndexOf("."))}):$lifeState")
    }

    fun <T> lifeStateF(lifeState: String, c : Class<T>) {
        val cName = c.name
        Log.v("###FragmentLife",
            "state(class:${cName.substring(cName.lastIndexOf("."))}):$lifeState")
    }


    fun <T> debugLog(msg: String,c : Class<T>? = null) {
        val cName = c?.name?:"."
        Log.d("###Debug","debug(${cName.substring(cName.lastIndexOf("."))}}):$msg")
    }

    fun errorLog(msg: String) {
        Log.e("###ERROR","error:$msg")
    }

    fun <T> runningLog(msg: String,c : Class<T>? = null){
        Log.d("###Running","msg(${c?.`package`?.name ?: ""}):$msg")
    }

    fun threadLog(tag : String){
        Log.e("###Thread",tag + "#######threadId:" + Thread.currentThread().id.toString() +
                "\n#####processTid:" + Process.myTid().toString() +
                "\n###isMainThread:" + (if(Looper.getMainLooper() == Looper.myLooper()) "true" else "false"))
    }

    fun netState(TAG: String, requestState: String) {
        Log.i("##Network","net(TAG:$TAG):$requestState")
    }
}