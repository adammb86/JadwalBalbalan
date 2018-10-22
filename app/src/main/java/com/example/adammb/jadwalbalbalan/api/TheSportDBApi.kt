package com.example.adammb.jadwalbalbalan.api

import android.net.Uri
import com.example.adammb.jadwalbalbalan.BuildConfig

object TheSportDBApi {
    fun getNextEvents(leagueId: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//                .appendPath("api")
//                .appendPath("v1")
//                .appendPath("json")
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath("eventsnextleague.php")
//                .appendQueryParameter("id", leagueId)
//                .build()
//                .toString()
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/eventsnextleague.php?id=" + leagueId
    }

    fun getPastEvents(leagueId: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//                .appendPath("api")
//                .appendPath("v1")
//                .appendPath("json")
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath("eventspastleague.php")
//                .appendQueryParameter("id", leagueId)
//                .build()
//                .toString()
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/eventspastleague.php?id=" + leagueId
    }

    fun getTeam(teamId: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//                .appendPath("api")
//                .appendPath("v1")
//                .appendPath("json")
//                .appendPath(BuildConfig.TSDB_API_KEY)
//                .appendPath("lookupteam.php")
//                .appendQueryParameter("id", teamId)
//                .build()
//                .toString()
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/lookupteam.php?id=" + teamId
    }
}