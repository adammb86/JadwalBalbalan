package com.example.adammb.jadwalbalbalan.model.league

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
        @SerializedName("idLeague")
        var leagueId: String? = null,

        @SerializedName("strLeague")
        var leagueName: String? = null,

        @SerializedName("strSport")
        var sportType: String? = null
) : Parcelable