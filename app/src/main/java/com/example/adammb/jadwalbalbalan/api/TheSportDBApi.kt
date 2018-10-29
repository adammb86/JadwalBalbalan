package com.example.adammb.jadwalbalbalan.api

import com.example.adammb.jadwalbalbalan.BuildConfig

object TheSportDBApi {
    fun getNextEvents(leagueId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/eventsnextleague.php?id=" + leagueId
    }

    fun getPastEvents(leagueId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/eventspastleague.php?id=" + leagueId
    }

    fun searchEvents(query: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/searchevents.php?e=" + query
    }

    fun searchTeams(query: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/searchteams.php?t=" + query
    }

    fun getTeam(teamId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/lookupteam.php?id=" + teamId
    }

    fun getTeams(leagueId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/lookup_all_teams.php?id=" + leagueId
    }

    fun getPlayers(teamId: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/lookup_all_players.php?id=" + teamId
    }

    fun getAllLeagues(): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/" +
                BuildConfig.TSDB_API_KEY +
                "/all_leagues.php?"
    }
}