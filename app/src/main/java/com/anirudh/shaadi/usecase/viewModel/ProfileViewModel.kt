package com.anirudh.shaadi.usecase.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anirudh.shaadi.usecase.repository.SearchRepository
import com.anirudh.shaadi.data.localDb.UserMatchesDb
import com.anirudh.shaadi.data.entity.ProfileInfo
import com.anirudh.shaadi.usecase.util.NetworkManager
import com.anirudh.shaadi.usecase.util.toEntityProfileInfo
import com.anirudh.shaadi.usecase.util.toProfilesInfoList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    val app: Application
) :
    AndroidViewModel(app) {

    private var _loadingProgressLiveData = MutableLiveData(false)
    var loadingProgressLiveData: LiveData<Boolean> = _loadingProgressLiveData

    private var _showError = MutableLiveData(false)
    var showError: LiveData<Boolean> = _showError

    private lateinit var db: UserMatchesDb

    private var _profilesList = MutableLiveData<List<ProfileInfo>?>()
    val profilesList: MutableLiveData<List<ProfileInfo>?> = _profilesList


    fun getProfilesList() {
        Log.d("Anirudh", "Outside coroutines")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Anirudh", " search")
            _loadingProgressLiveData.postValue(true)
            if (NetworkManager.isConnectionAvailable(app.applicationContext)) {
                /* if network available,sync localDb with server */
                val result =
                    searchRepository.getProfiles()
                _loadingProgressLiveData.postValue(false)
                if (result.isSuccessful) {
                    val results = result.body()?.profileInfos
                    saveInDb(results)
                    _profilesList.postValue(results)
                    Log.d("Anirudh", "$results success")
                } else {
                    Log.d("Anirudh", "$ success results fetched from Db")
                    fetchFromDb()
                }
            } else {
                /* if network not available,fetch Data from Db */
                fetchFromDb()
                _loadingProgressLiveData.postValue(false)
            }

        }
    }

    fun initializeDb(context: Context) {
        db = UserMatchesDb(context)
    }

    private fun saveInDb(profileInfo: List<ProfileInfo>?) {
        viewModelScope.launch(Dispatchers.IO) {
            db.profilesDao().clearDb()
            val profilesList = profileInfo?.toEntityProfileInfo() ?: emptyList()
            db.profilesDao()
                .insertProfiles(items = profilesList)
        }
    }

    private fun fetchFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = db.profilesDao().getProfiles()
            _profilesList.postValue(items.toProfilesInfoList())
        }
    }

    fun handleError() {
        _showError.postValue(true)
    }

    fun updateUserProfileInDb(profileInfo: ProfileInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            db.profilesDao().insertProfileInfo(profileInfo.toEntityProfileInfo())
        }
    }

}