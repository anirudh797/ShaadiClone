package com.anirudh.shaadi.domain.repository

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.anirudh.shaadi.data.entity.ProfileInfo
import com.anirudh.shaadi.data.entity.ProfileStatus
import com.anirudh.shaadi.data.entity.Profiles
import com.anirudh.shaadi.data.localDb.UserMatchesDb
import com.anirudh.shaadi.data.remote.ProfilesApi
import com.anirudh.shaadi.domain.util.NetworkManager
import com.anirudh.shaadi.domain.util.toEntityProfileInfoList
import com.anirudh.shaadi.domain.util.toProfilesInfoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val profilesApi: ProfilesApi,
    val app: Application
) : ISearchRepository {

    private lateinit var db: UserMatchesDb

    init {
        initializeDb(app.applicationContext)
    }

    override suspend fun getProfiles(): List<ProfileInfo> {

        val result = withContext(Dispatchers.IO) {
            if (NetworkManager.isConnectionAvailable(app.applicationContext)) {
                /* if network available,sync localDb with server */
                try {
                    val result = profilesApi.getProfiles()
                    if (result.isSuccessful) {
                        val results = result.body()?.profileInfos
                        saveInDb(results)
                        Log.d("Anirudh", "$results success")
                        results ?: emptyList()
                    } else {
                        /* if api fails ,try to fetch from Db */
                        Log.d("Anirudh", "$ success results fetched from Db")
                        fetchFromDb()
                    }
                } catch (ex: Exception) {
                    fetchFromDb()
                }

            } else {
                Log.d("Anirudh", "Trying to fetch From DB")
                /* if network not available,fetch Data from Db */
                fetchFromDb()
            }
        }
        return result
    }

    private fun initializeDb(context: Context) {
        db = UserMatchesDb(context)
    }

    private suspend fun saveInDb(profileInfos: List<ProfileInfo>?) {
        withContext(Dispatchers.IO) {
            db.profilesDao().clearDb()
            val profilesList = profileInfos?.toEntityProfileInfoList() ?: emptyList()
            if (profilesList.isNotEmpty()) {
                db.profilesDao()
                    .insertProfiles(items = profilesList)
            }
        }
    }

    private suspend fun fetchFromDb(): List<ProfileInfo> {
        val profilesList = withContext(Dispatchers.IO) {
            try {
                val items = db.profilesDao().getProfiles()
                Log.d("Anirudh", "fetch From DB $items")
                if (items.isNotEmpty()) {
                    items.toProfilesInfoList()
                } else {
                    emptyList()
                }
            } catch (e: SQLiteException) {
                emptyList()
            }
        }
        return profilesList
    }

    suspend fun updateUserProfileInDb(profileInfo: ProfileInfo) {
        withContext(Dispatchers.IO) {
            db.profilesDao().let {
                val userProfile = it.getProfileByEmail(profileInfo.email)
                Log.d("Anirudh", "trying to fetch profile By Email $userProfile")
                userProfile.status = profileInfo.profileStatus ?: ProfileStatus.NONE
                it.updateProfileInfo(userProfile)
                Log.d("Anirudh", "updated profile $userProfile")
            }
        }
    }

}