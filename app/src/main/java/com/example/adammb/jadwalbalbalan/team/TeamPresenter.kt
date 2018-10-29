package com.example.adammb.jadwalbalbalan.team

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.database.database
import com.example.adammb.jadwalbalbalan.model.league.LeagueResponse
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TABEL_FAVORITE_TEAM
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class TeamPresenter(private val view: TeamContract.TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) : TeamContract.TeamPresenter {

    override fun getLeagueList() {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getAllLeagues()),
                        LeagueResponse::class.java
                )
            }

            view.showLeagueList(data.await().leagues)
        }
    }

    override fun getTeamList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getTeams(leagueId)),
                        TeamResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    override fun getFavoriteTeamList() {
        view.getContextFromFragment()?.database?.use {
            val result = select(TABEL_FAVORITE_TEAM)
            val favorites = result.parseList(classParser<Team>())
            view.showTeamList(favorites)
        }
    }
}