package com.example.adammb.jadwalbalbalan.team

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.league.League
import com.example.adammb.jadwalbalbalan.model.team.Team

interface TeamContract {
    interface TeamView {
        fun getContextFromFragment(): Context?

        fun showLoading()

        fun hideLoading()

        fun showLeagueList(leagues: List<League>)

        fun showTeamList(teams: List<Team>)
    }

    interface TeamPresenter {
        fun getLeagueList()

        fun getTeamList(leagueId: String?)

        fun getFavoriteTeamList()
    }
}