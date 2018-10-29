package com.example.adammb.jadwalbalbalan.event

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.database.database
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.event.EventResponse
import com.example.adammb.jadwalbalbalan.model.league.LeagueResponse
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class EventPresenter(private val view: EventContract.EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) : EventContract.EventPresenter {

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

    override fun getPrevEventList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getPastEvents(leagueId)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    override fun getNextEventList(leagueId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getNextEvents(leagueId)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    override fun getFavoriteEventList() {
        view.getContextFromFragment()?.database?.use {
            val result = select(Event.TABEL_FAVORITE_MATCH)
            val favorites = result.parseList(classParser<Event>())
            view.showEventList(favorites)
        }
    }
}