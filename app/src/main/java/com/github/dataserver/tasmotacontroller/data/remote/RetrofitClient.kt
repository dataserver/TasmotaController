package com.github.dataserver.tasmotacontroller.data.remote

import com.github.dataserver.tasmotacontroller.Constants.PLACEHOLDER_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null

    @JvmStatic
    val client: Retrofit?
        get() {
            val client = provideOkHttpClient()
            if (retrofit == null) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(PLACEHOLDER_BASE_URL)
                    .client(client)
                    .build()
            }
            return retrofit
        }

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Log request and response body

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
