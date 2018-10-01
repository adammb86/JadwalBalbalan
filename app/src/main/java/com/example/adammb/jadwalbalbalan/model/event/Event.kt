package com.example.adammb.jadwalbalbalan.model.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        @SerializedName("dateEvent")
        var date: String? = null,

        @SerializedName("idHomeTeam")
        var teamHomeId: String? = null,

        @SerializedName("strHomeTeam")
        var teamHomeName: String? = null,

        @SerializedName("intHomeScore")
        var teamHomeScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var teamHomeGoalDetails: String? = null,

        @SerializedName("intHomeShots")
        var teamHomeShots: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var teamHomeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var teamHomeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var teamHomeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var teamHomeLineupForward: String? = null,

        @SerializedName("idAwayTeam")
        var teamAwayId: String? = null,

        @SerializedName("strAwayTeam")
        var teamAwayName: String? = null,

        @SerializedName("intAwayScore")
        var teamAwayScore: String? = null,

        @SerializedName("strAwayGoalDetails")
        var teamAwayGoalDetails: String? = null,

        @SerializedName("intAwayShots")
        var teamAwayShots: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var teamAwayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var teamAwayLineupDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var teamAwayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var teamAwayLineupForward: String? = null
) : Parcelable