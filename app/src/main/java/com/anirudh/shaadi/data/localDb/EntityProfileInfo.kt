package com.anirudh.shaadi.data.localDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.anirudh.shaadi.data.entity.ProfileStatus

@Entity(tableName = "profile_info")
@TypeConverters(Converters::class)
data class EntityProfileInfo(
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "country")
    val state: String,
    @ColumnInfo(name = "state")
    val country: String,
    @ColumnInfo(name = "status")
    val status: ProfileStatus
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profileId")
    var profileId: Int = 0
}