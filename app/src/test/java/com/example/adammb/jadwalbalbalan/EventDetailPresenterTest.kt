package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.eventdetail.EventDetailContract
import com.example.adammb.jadwalbalbalan.eventdetail.EventDetailPresenter
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {

    @Mock
    private lateinit var view: EventDetailContract.EventDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeam() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val teamId = "133604"
        val type = EventDetailPresenter.TEAM_HOME

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(teamId)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeam(teamId, type)

        verify(view).showLogo(teams, type)
    }
}