package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.player.Player
import com.example.adammb.jadwalbalbalan.model.player.PlayerResponse
import com.example.adammb.jadwalbalbalan.teamdetail.player.PlayerContract
import com.example.adammb.jadwalbalbalan.teamdetail.player.PlayerPresenter
import com.example.adammb.jadwalbalbalan.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private lateinit var view: PlayerContract.PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayerList() {
        val players: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(players)
        val teamId = "133602"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayers(teamId)),
                PlayerResponse::class.java
        )).thenReturn(response)

        presenter.getPlayerList(teamId)

        verify(view).showLoading()
        verify(view).showPlayerList(players)
        verify(view).hideLoading()
    }
}