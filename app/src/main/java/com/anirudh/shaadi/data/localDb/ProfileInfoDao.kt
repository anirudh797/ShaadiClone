package com.anirudh.shaadi.data.localDb

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileInfoDao {

    @Throws(SQLiteException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileInfo(item: EntityProfileInfo)

    @Throws(SQLiteException::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfiles(items: List<EntityProfileInfo>)

    @Throws(SQLiteException::class)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateProfileInfo(profileInfo : EntityProfileInfo)
    @Throws(SQLiteException::class)
    @Query("SELECT * FROM profile_info WHERE email = :email")
    fun getProfileByEmail(email: String): EntityProfileInfo

    @Throws(SQLiteException::class)
    @Query("SELECT * FROM profile_info")
    fun getProfiles(): List<EntityProfileInfo>

    @Throws(SQLiteException::class)
    @Query("DELETE FROM profile_info")
    fun clearDb()
}
