package com.example.a2do_parcial_progra3.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dummyJsonApiService: DummyJsonApiService by lazy {
        createRetrofit("https://dummyjson.com/").create(DummyJsonApiService::class.java)
    }

    val frankfurterApiService: FrankfurterApiService by lazy {
        createRetrofit("https://api.frankfurter.dev/").create(FrankfurterApiService::class.java)
    }

    val jsonPlaceholderApiService: JsonPlaceholderApiService by lazy {
        createRetrofit("https://jsonplaceholder.typicode.com/").create(JsonPlaceholderApiService::class.java)
    }
}
