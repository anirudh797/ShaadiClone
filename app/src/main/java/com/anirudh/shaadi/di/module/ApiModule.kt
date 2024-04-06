package com.anirudh.shaadi.di.module

import com.anirudh.shaadi.data.remote.ProfilesApi
import com.anirudh.shaadi.data.remote.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    /*
   * The method returns the Gson object
   * */
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideSearchApiService(gson: Gson): ProfilesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(RetrofitInstance.BASE_URL)
            .build().create(ProfilesApi::class.java)
    }

}