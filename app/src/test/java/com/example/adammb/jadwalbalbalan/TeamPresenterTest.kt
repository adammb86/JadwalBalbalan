package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.league.League
import com.example.adammb.jadwalbalbalan.model.league.LeagueResponse
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.team.TeamContract
import com.example.adammb.jadwalbalbalan.team.TeamPresenter
import com.example.adammb.jadwalbalbalan.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {

    @Mock
    private lateinit var view: TeamContract.TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val leagueId = "4238"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams(leagueId)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(leagueId)

        verify(view).showLoading()
        verify(view).showTeamList(teams)
        verify(view).hideLoading()
    }

    @Test
    fun getLeagueList() {
        val leagues: MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAllLeagues()),
                LeagueResponse::class.java
        )).thenReturn(response)

        presenter.getLeagueList()

        verify(view).showLoading()
        verify(view).showLeagueList(leagues)
    }
}