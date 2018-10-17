package com.example.adammb.jadwalbalbalan.model.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        val id: Long?,

        @SerializedName("idEvent")
        var eventId: String? = null,

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
) : Parcelable {
    companion object Favorite {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val DATE: String = "DATE"
        const val TEAM_HOME_ID: String = "TEAM_HOME_ID"
        const val TEAM_HOME_NAME: String = "TEAM_HOME_NAME"
        const val TEAM_HOME_SCORE: String = "TEAM_HOME_SCORE"
        const val TEAM_HOME_GOAL_DETAILS: String = "TEAM_HOME_GOAL_DETAILS"
        const val TEAM_HOME_SHOTS: String = "TEAM_HOME_SHOTS"
        const val TEAM_HOME_LINEUP_GOALKEEPER: String = "TEAM_HOME_LINEUP_GOALKEEPER"
        const val TEAM_HOME_LINEUP_DEFENSE: String = "TEAM_HOME_LINEUP_DEFENSE"
        const val TEAM_HOME_LINEUP_MIDFIELD: String = "TEAM_HOME_LINEUP_MIDFIELD"
        const val TEAM_HOME_LINEUP_FORWARD: String = "TEAM_HOME_LINEUP_FORWARD"
        const val TEAM_AWAY_ID: String = "TEAM_AWAY_ID"
        const val TEAM_AWAY_NAME: String = "TEAM_AWAY_NAME"
        const val TEAM_AWAY_SCORE: String = "TEAM_AWAY_SCORE"
        const val TEAM_AWAY_GOAL_DETAILS: String = "TEAM_AWAY_GOAL_DETAILS"
        const val TEAM_AWAY_SHOTS: String = "TEAM_AWAY_SHOTS"
        const val TEAM_AWAY_LINEUP_GOALKEEPER: String = "TEAM_AWAY_LINEUP_GOALKEEPER"
        const val TEAM_AWAY_LINEUP_DEFENSE: String = "TEAM_AWAY_LINEUP_DEFENSE"
        const val TEAM_AWAY_LINEUP_MIDFIELD: String = "TEAM_AWAY_LINEUP_MIDFIELD"
        const val TEAM_AWAY_LINEUP_FORWARD: String = "TEAM_AWAY_LINEUP_FORWARD"
    }
}