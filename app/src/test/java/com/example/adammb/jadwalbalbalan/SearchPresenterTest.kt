package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.event.EventResponse
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.search.SearchContract
import com.example.adammb.jadwalbalbalan.search.SearchPresenter
import com.example.adammb.jadwalbalbalan.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    @Mock
    private lateinit var view: SearchContract.SearchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun searchEvents() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val query = "liverpool"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchEvents(query)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.searchEvents(query)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }

    @Test
    fun searchTeams() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val query = "liverpool"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchTeams(query)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.searchTeams(query)

        verify(view).showLoading()
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }
}