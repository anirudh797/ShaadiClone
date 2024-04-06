package com.anirudh.shaadi.data.localDb

import androidx.room.TypeConverter
import com.anirudh.shaadi.data.model.ProfileStatus

class Converters {

    @TypeConverter
    fun toProfileStatus(value: Int) = enumValues<ProfileStatus>()[value]

    @TypeConverter
    fun fromProfileStatus(value: ProfileStatus) = value.ordinal
}
