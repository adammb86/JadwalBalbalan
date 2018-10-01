package com.example.adammb.jadwalbalbalan.event

import com.example.adammb.jadwalbalbalan.model.event.Event

interface EventContract {
    interface EventView {
        fun showLoading()

        fun hideLoading()

        fun showEventList(events: List<Event>)
    }

    interface EventPresenter {
        fun getPrevEventList(leagueId: String?)

        fun getNextEventList(leagueId: String?)
    }
}