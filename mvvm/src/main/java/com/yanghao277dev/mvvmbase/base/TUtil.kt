package com.yanghao277dev.mvvmbase.base

import java.lang.reflect.ParameterizedType

object TUtil {
    fun <T> getNewInstance(any: Any?, i: Int): T? {
        if (any != null) {
            try {
                return ((any.javaClass.genericSuperclass as ParameterizedType)
                    .actualTypeArguments[i] as Class<T>)
                    .newInstance()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun <T> getInstance(any: Any?, i: Int): T ? {
        return if (any != null) {
            (any.javaClass
                .genericSuperclass as ParameterizedType)
                .actualTypeArguments[i] as T
        } else null
    }

    fun <T> checkNotNull(reference: T?): T {
        if (reference == null) {
            throw NullPointerException()
        }
        return reference
    }
}