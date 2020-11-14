package com.yanghao277dev.mvvmbase.http

import android.content.Context
import com.google.gson.GsonBuilder
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class HttpHelper private constructor() {
    private class Builder(private val mContext: Context,private val isLog : Boolean) {
        var mOkHttpClient: OkHttpClient? = null
        var mBuilder: OkHttpClient.Builder? = null
        var mRetrofit: Retrofit? = null

        fun initOkHttp(timeS: Long): Builder {
            val interceptor = if(isLog) HttpLoggingInterceptor(HttpLogger()) else null
            interceptor?.setLevel(HttpLoggingInterceptor.Level.BODY)
            if (mBuilder == null) {
                synchronized(HttpHelper::class.java) {
                    if (mBuilder == null) {
                        val cache = Cache(File(mContext.cacheDir, "HttpCache"), 1024 * 1024 * 10)
                        mBuilder = OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(timeS, TimeUnit.SECONDS)
                            .writeTimeout(timeS, TimeUnit.SECONDS)
                            .readTimeout(timeS, TimeUnit.SECONDS)
                        interceptor?.let{
                            mBuilder?.addInterceptor(it)
                        }
                    }
                }
            }
            return this
        }

        fun addHttpLogger(httpLogger: HttpLogger?) : Builder {
            httpLogger?.let{
                val interceptor = HttpLoggingInterceptor(it)
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                mBuilder?.addInterceptor(interceptor)
            }
            return this
        }

        /**
         *
         * @param mInterceptor Interceptor
         * @return Builder
         */
        fun addNetworkInterceptor(mInterceptor: Interceptor?): Builder {
            return this.apply {
                mInterceptor?.let{
                    mBuilder?.addNetworkInterceptor(it)
                }
            }
        }

        /**
         * create retrofit
         *
         * @param baseUrl baseUrl
         * @return Builder
         */
        fun createRetrofit(baseUrl: String): Builder {
            val builder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .baseUrl(baseUrl)
            return this.apply {
                mOkHttpClient = RetrofitUrlManager.getInstance().with(mBuilder).build()

                RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl)
                RetrofitUrlManager.getInstance().startAdvancedModel(baseUrl)

                mOkHttpClient?.let{
                    mRetrofit = builder.client(it).build()
                }
            }
        }

        fun build() {
            instance.build(this)
        }
    }

    private fun build(builder: Builder) {
        mBuilder = builder.mBuilder
        mOkHttpClient = builder.mOkHttpClient
        mRetrofit = builder.mRetrofit
    }

    fun <T> create(clz: Class<T>): T? {
        return mRetrofit?.create(clz)
    }

    fun resetBaseUrlWithKey(key : String,baseUrl : String){
        RetrofitUrlManager.getInstance().putDomain(key, baseUrl)
    }

    companion object {
        private var mOkHttpClient: OkHttpClient? = null
        private var mRetrofit: Retrofit? = null
        private var mBuilder: OkHttpClient.Builder? = null

        val instance : HttpHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpHelper()
        }

        fun init(context: Context, baseUrl: String,
                 timeS: Long = 30, httpLogger: HttpLogger? = null,
                 isLog: Boolean = false,) {

            Builder(context,isLog)
                .initOkHttp(timeS)
                .addHttpLogger(httpLogger)
                .createRetrofit(baseUrl)
                .build()
            RetrofitUrlManager.getInstance().startAdvancedModel(baseUrl)

        }
    }
}