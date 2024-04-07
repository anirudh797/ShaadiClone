package com.anirudh.shaadi.domain.repository
import com.anirudh.shaadi.data.entity.ProfileInfo
import com.anirudh.shaadi.data.entity.Profiles
import retrofit2.Response

interface ISearchRepository {
    suspend fun getProfiles(
    ): List<ProfileInfo>?

}