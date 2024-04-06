package com.anirudh.shaadi.usecase.repository
import com.anirudh.shaadi.data.model.Profiles
import com.anirudh.shaadi.data.remote.ProfilesApi
import com.anirudh.shaadi.usecase.repository.ISearchRepository
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val profilesApi: ProfilesApi,
) : ISearchRepository {
    override suspend fun getProfiles(): Response<Profiles> {
        return profilesApi.getProfiles()
    }


}