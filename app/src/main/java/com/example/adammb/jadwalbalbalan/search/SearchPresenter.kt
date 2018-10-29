package com.example.adammb.jadwalbalbalan.search

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.event.EventResponse
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchPresenter(private val view: SearchContract.SearchView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) : SearchContract.SearchPresenter {
    override fun searchEvents(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.searchEvents(query)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    override fun searchTeams(query: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.searchTeams(query)),
                        TeamResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}