package com.anirudh.shaadi.usecase.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anirudh.shaadi.usecase.repository.SearchRepository
import com.anirudh.shaadi.data.localDb.EntityProfileInfo
import com.anirudh.shaadi.data.localDb.UserMatchesDb
import com.anirudh.shaadi.data.model.ProfileInfo
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Reusable
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    private var _loadingProgressLiveData = MutableLiveData(false)
    var loadingProgressLiveData: LiveData<Boolean> = _loadingProgressLiveData

    lateinit var db: UserMatchesDb

    private var _profilesList = MutableLiveData<List<ProfileInfo>?>()
    val profilesList: MutableLiveData<List<ProfileInfo>?> = _profilesList


    fun getProfilesList() {
        Log.d("Anirudh", "Outside coroutines")
        viewModelScope.launch(Dispatchers.Default) {
            Log.d("Anirudh", " search")
            _loadingProgressLiveData.postValue(true)
            val result =
                searchRepository.getProfiles()
            if (result.isSuccessful) {
                _loadingProgressLiveData.postValue(false)
                val results = result.body()?.profileInfos
//                saveInDb(result.body())
                _profilesList.postValue(results)
                Log.d("Anirudh", "$results success")
            } else {
                _loadingProgressLiveData.postValue(false)
                Log.d("Anirudh", " failure")
            }
        }
    }

    fun initializeDb(context: Context) {
//        db = ItemInfoDb(context)
    }

    private fun saveInDb(profileInfo: ProfileInfo?) {
        viewModelScope.launch(Dispatchers.IO) {
            db.ItemInfoDao().clearDb()
            val list = mutableListOf<EntityProfileInfo>()
            profileInfo?.let {
            }
            if (list.isNotEmpty()) {
                db.ItemInfoDao().insertProfiles(items = list)
            }
        }
    }

    private fun fetchFromDb() {
        _loadingProgressLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val items = db.ItemInfoDao().getProfiles()
//            _profilesList.postValue(items)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun showLastProfilesList() {
        fetchFromDb()
    }

    fun userProfileDeclined(profileInfo: ProfileInfo) {

    }

    fun userProfileAccepted(profileInfo: ProfileInfo) {

    }

}