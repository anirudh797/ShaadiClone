package com.anirudh.shaadi.data.remote

import com.anirudh.shaadi.data.model.Profiles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfilesApi {
    companion object {
        const val PAGE_SIZE = "10"
    }

    @GET("api/")
    suspend fun getProfiles(
        @Query("results") type: String = PAGE_SIZE,
    ): Response<Profiles>

}
