package com.anirudh.shaadi.data.remote

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "https://randomuser.me/"

//    val api: ProfilesApi by lazy {
//        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
//            GsonConverterFactory.create()
//        ).build().create(ProfilesApi::class.java)
//    }

}