package com.example.sportsapp.utilities

import com.example.sportsapp.data.SportsDataApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val baseURL: String = "https://www.thesportsdb.com/api/v1/json/"

    val api: SportsDataApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportsDataApi::class.java)
    }
}