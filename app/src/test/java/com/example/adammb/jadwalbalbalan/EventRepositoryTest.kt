package com.example.adammb.jadwalbalbalan

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.event.EventContract
import com.example.adammb.jadwalbalbalan.event.EventPresenter
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.event.EventResponse
import com.example.adammb.jadwalbalbalan.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private lateinit var view: EventContract.EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPrevEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4238"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastEvents(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getPrevEventList(leagueId)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }

    @Test
    fun getNextEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4238"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextEvents(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventList(leagueId)

        verify(view).showLoading()
        verify(view).showEventList(events)
        verify(view).hideLoading()
    }
}