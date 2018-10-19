package com.example.adammb.jadwalbalbalan.event

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.event.Event

interface EventContract {
    interface EventView {
        fun getContextFromFragment(): Context?

        fun showLoading()

        fun hideLoading()

        fun showEventList(events: List<Event>)
    }

    interface EventPresenter {
        fun getPrevEventList(leagueId: String?)

        fun getNextEventList(leagueId: String?)

        fun getFavoriteEventList()
    }
}