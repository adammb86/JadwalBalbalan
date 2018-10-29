package com.example.adammb.jadwalbalbalan.search

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.team.Team

interface SearchContract {
    interface SearchView {
        fun getContext(): Context?

        fun showLoading()

        fun hideLoading()

        fun showEventList(events: List<Event>)

        fun showTeamList(teams: List<Team>)
    }

    interface SearchPresenter {
        fun searchEvents(query: String?)

        fun searchTeams(query: String?)
    }
}