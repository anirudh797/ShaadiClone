package com.anirudh.shaadi.usecase.repository
import com.anirudh.shaadi.data.entity.Profiles
import retrofit2.Response

interface ISearchRepository {
    suspend fun getProfiles(
    ): Response<Profiles>

}