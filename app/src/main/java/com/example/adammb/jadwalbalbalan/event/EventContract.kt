package com.example.adammb.jadwalbalbalan.event

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.league.League

interface EventContract {
    interface EventView {
        fun getContextFromFragment(): Context?

        fun showLoading()

        fun hideLoading()

        fun showLeagueList(leagues: List<League>)

        fun showEventList(events: List<Event>)
    }

    interface EventPresenter {
        fun getLeagueList()

        fun getPrevEventList(leagueId: String?)

        fun getNextEventList(leagueId: String?)

        fun getFavoriteEventList()
    }
}