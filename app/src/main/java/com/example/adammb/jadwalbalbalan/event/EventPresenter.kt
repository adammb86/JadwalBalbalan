package com.example.adammb.jadwalbalbalan.event

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.event.EventResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventPresenter(private val view: EventContract.EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson) : EventContract.EventPresenter {

    override fun getPrevEventList(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getPastEvents(leagueId)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(data.events)
            }
        }
    }

    override fun getNextEventList(leagueId: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getNextEvents(leagueId)),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showEventList(data.events)
            }
        }
    }

    override fun getFavoriteEventList() {
        view.getContextFromFragment()?.database?.use {
            val result = select(Event.TABLE_FAVORITE)
            val favorites = result.parseList(classParser<Event>())
            view.showEventList(favorites)
        }
    }
}