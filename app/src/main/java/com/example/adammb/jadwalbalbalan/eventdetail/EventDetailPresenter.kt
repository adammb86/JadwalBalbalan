package com.example.adammb.jadwalbalbalan.eventdetail

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailPresenter(private val view: EventDetailContract.EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) : EventDetailContract.EventDetailPresenter {

    companion object {
        const val TEAM_HOME = "team-home"
        const val TEAM_AWAY = "team-away"
    }

    override fun getTeam(teamId: String?, type: String?) {
        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeam(teamId)),
                    TeamResponse::class.java
            )

            uiThread {
                view.showLogo(data.teams[0].teamBadge, type)
            }
        }
    }
}