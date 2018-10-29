package com.example.adammb.jadwalbalbalan.teamdetail

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.team.Team

interface TeamDetailContract {
    interface TeamDetailView {
        fun getContext(): Context

        fun showFavoriteState(isFavorited: Boolean)
    }

    interface TeamDetailPresenter {
        fun getFavoriteState(teamId: String): Boolean

        fun addToFavorite(team: Team?)

        fun removeFromFavorite(teamId: String)
    }
}