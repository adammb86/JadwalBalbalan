package com.example.adammb.jadwalbalbalan.teamdetail.player

import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.model.player.PlayerResponse
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerContract.PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) : PlayerContract.PlayerPresenter {

    override fun getPlayerList(teamId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(teamId)),
                        PlayerResponse::class.java
                )
            }

            view.showPlayerList(data.await().players)
            view.hideLoading()
        }
    }
}