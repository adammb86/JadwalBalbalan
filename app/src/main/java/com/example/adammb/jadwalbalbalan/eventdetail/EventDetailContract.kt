package com.example.adammb.jadwalbalbalan.eventdetail

import android.content.Context
import com.example.adammb.jadwalbalbalan.model.event.Event

interface EventDetailContract {
    interface EventDetailView {
        fun getContext(): Context

        fun showLogo(url: String?, type: String?)

        fun showFavoriteState(isFavorited: Boolean)
    }

    interface EventDetailPresenter {
        fun getTeam(teamId: String?, type: String?)

        fun getFavoriteState(eventId: String): Boolean

        fun addToFavorite(event: Event?)

        fun removeFromFavorite(eventId: String)
    }
}