package com.anirudh.shaadi.usecase.repository
import com.anirudh.shaadi.data.entity.Profiles
import com.anirudh.shaadi.data.remote.ProfilesApi
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val profilesApi: ProfilesApi,
) : ISearchRepository {
    override suspend fun getProfiles(): Response<Profiles> {
        return profilesApi.getProfiles()
    }


}