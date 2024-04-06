package com.anirudh.shaadi.data.entity

import com.google.gson.annotations.SerializedName

data class Profiles(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val profileInfos: List<ProfileInfo>? = null
)