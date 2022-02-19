package com.mywork.templateapp.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mywork.templateapp.data.api.ApiInterface
import com.mywork.templateapp.utils.Constants.Companion.BASE_URL
import com.mywork.templateapp.utils.network.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//Think of the module as the “bag” from where we will get our dependencies from.

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Retrofit
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Singleton
    @Provides
    fun provideHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        sharedPreferences: SharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(interceptor = { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Accept", "application/json")
//                requestBuilder.header(
//                    "X-localization",
//                    sharedPreferences.getString(PreferenceKeys.USER_LANGUAGE, "en") ?: "en"
//                )
//
//                if (!accessToken.isNullOrEmpty())
//                    requestBuilder.addHeader("authorization", "Bearer $accessToken")

                chain.proceed(requestBuilder.build())
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson, httpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApiInterfaceService(retrofitBuilder: Retrofit.Builder): ApiInterface {
        return retrofitBuilder
            .build()
            .create(ApiInterface::class.java)
    }
}