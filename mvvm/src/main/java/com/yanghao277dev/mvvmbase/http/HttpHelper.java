package com.yanghao277dev.mvvmbase.http;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.yanghao277dev.mvvmbase.base.TUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static volatile HttpHelper mHttpHelper = null;

    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static OkHttpClient.Builder mBuilder;
    private static String BASE_URL;

    private HttpHelper() {

    }

    public static HttpHelper getInstance() {
        if (mHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null) {
                    mHttpHelper = new HttpHelper();
                }
            }
        }
        return mHttpHelper;
    }

    public static void init(Context context, String baseUrl) {
        new HttpHelper.Builder(context)
                .initOkHttp()
                .createRetrofit(baseUrl)
                .build();
    }


    public static class Builder {
        private OkHttpClient mOkHttpClient;

        private OkHttpClient.Builder mBuilder;

        private Retrofit mRetrofit;

        private Context mContext;
        public Builder(Context context) {
            this.mContext=context;
        }

        /**
         * create OkHttp 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志
         *
         * @return Builder
         */
        public Builder initOkHttp() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLogger());
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (mBuilder == null) {
                synchronized (HttpHelper.class) {
                    if (mBuilder == null) {
                        Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                        mBuilder = new OkHttpClient.Builder()
                                .cache(cache)
                                .addInterceptor(interceptor)
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS);
                    }
                }
            }

            return this;
        }

        /**
         *
         * @param mInterceptor Interceptor
         * @return Builder
         */
        public Builder addInterceptor(Interceptor mInterceptor) {
            TUtil.INSTANCE.checkNotNull(mInterceptor);
            this.mBuilder.addNetworkInterceptor(mInterceptor);
            return this;
        }

        /**
         * create retrofit
         *
         * @param baseUrl baseUrl
         * @return Builder
         */
        public Builder createRetrofit(String baseUrl) {
            TUtil.INSTANCE.checkNotNull(baseUrl);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .baseUrl(baseUrl);
            BASE_URL = baseUrl;
            this.mOkHttpClient = mBuilder.build();
            this.mRetrofit = builder.client(mOkHttpClient)
                    .build();
            return this;
        }

        public void build() {
            HttpHelper.getInstance().build(this);
        }
    }

    private void build(Builder builder) {

        TUtil.INSTANCE.checkNotNull(builder);
        TUtil.INSTANCE.checkNotNull(builder.mBuilder);
        TUtil.INSTANCE.checkNotNull(builder.mOkHttpClient);
        TUtil.INSTANCE.checkNotNull(builder.mRetrofit);
        mBuilder = builder.mBuilder;
        mOkHttpClient = builder.mOkHttpClient;
        mRetrofit = builder.mRetrofit;
    }

    public <T> T create(Class<T> clz) {
        TUtil.INSTANCE.checkNotNull(clz);
        TUtil.INSTANCE.checkNotNull(mRetrofit);
        return mRetrofit.create(clz);
    }

}
