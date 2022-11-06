package com.hasandeniz.bitirmeprojesi.retrofit

import com.hasandeniz.bitirmeprojesi.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val builder = OkHttpClient.Builder()
        fun getClient(baseurl:String) : Retrofit{
            if (BuildConfig.DEBUG)
                builder.addInterceptor(OkHttpProfilerInterceptor())
            val client = builder.build()
            return Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}