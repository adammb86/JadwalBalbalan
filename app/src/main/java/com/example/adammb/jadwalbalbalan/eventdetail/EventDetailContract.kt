package com.example.adammb.jadwalbalbalan.eventdetail

interface EventDetailContract {
    interface EventDetailView {
        fun showLogo(url: String?, type: String?)
    }

    interface EventDetailPresenter {
        fun getTeam(teamId: String?, type: String?)
    }
}