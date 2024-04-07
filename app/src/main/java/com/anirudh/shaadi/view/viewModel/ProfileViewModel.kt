package com.anirudh.shaadi.view.viewModel

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anirudh.shaadi.domain.repository.SearchRepository
import com.anirudh.shaadi.data.localDb.UserMatchesDb
import com.anirudh.shaadi.data.entity.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) :
    ViewModel() {

    private var _loadingProgressLiveData = MutableLiveData(false)
    var loadingProgressLiveData: LiveData<Boolean> = _loadingProgressLiveData

    private var _showError = MutableLiveData(false)
    var showError: LiveData<Boolean> = _showError

    private var _profilesList = MutableLiveData<List<ProfileInfo>>()
    val profilesList: MutableLiveData<List<ProfileInfo>> = _profilesList

    fun getProfilesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingProgressLiveData.postValue(true)
            val profilesList = searchRepository.getProfiles()
            _loadingProgressLiveData.postValue(false)
            if (profilesList.isEmpty()) {
                _showError.postValue(true)
            } else {
                _profilesList.postValue(profilesList)
                _showError.postValue(false)
            }
        }
    }


    fun updateUserProfileInDb(profileInfo: ProfileInfo) {
        viewModelScope.launch {
            try {
                searchRepository.updateUserProfileInDb(profileInfo)
            } catch (e: SQLiteException) {
                _showError.postValue(true)
            }
        }
    }

}