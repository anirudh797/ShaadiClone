package com.anirudh.shaadi.data.localDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileInfo(item: EntityProfileInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(items: List<EntityProfileInfo>)

//    @Query("SELECT * FROM item_info WHERE id=:id")
//    suspend fun getItemById(id: Int): LiveData<EntityItemInfo>

    @Query("SELECT * FROM profile_info")
    fun getProfiles(): List<EntityProfileInfo>

    @Query("DELETE FROM profile_info")
    fun clearDb()
}
