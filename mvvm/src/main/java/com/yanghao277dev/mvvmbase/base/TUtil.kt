package com.yanghao277dev.mvvmbase.base

import java.lang.reflect.ParameterizedType

object TUtil {
    fun <T> getInstance(any: Any?, i: Int): T ? {
        return if (any != null) {
            (any.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[i] as T
        } else null
    }
}