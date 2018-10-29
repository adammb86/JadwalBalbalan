package com.example.adammb.jadwalbalbalan.teamdetail.player

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.player.Player

interface PlayerContract {
    interface PlayerView {
        fun getContextFromFragment(): Context?

        fun showLoading()

        fun hideLoading()

        fun showPlayerList(players: List<Player>)
    }

    interface PlayerPresenter {
        fun getPlayerList(teamId: String?)
    }
}