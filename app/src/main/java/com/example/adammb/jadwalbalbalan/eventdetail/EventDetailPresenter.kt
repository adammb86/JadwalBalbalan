package com.example.adammb.jadwalbalbalan.eventdetail

import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.api.TheSportDBApi
import com.example.adammb.jadwalbalbalan.database.database
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.team.TeamResponse
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class EventDetailPresenter(private val view: EventDetailContract.EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) : EventDetailContract.EventDetailPresenter {
    companion object {
        const val TEAM_HOME = "team-home"
        const val TEAM_AWAY = "team-away"
    }

    override fun getTeam(teamId: String?, type: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getTeam(teamId)),
                        TeamResponse::class.java
                )
            }

            view.showLogo(data.await().teams, type)
        }
    }

    override fun getFavoriteState(eventId: String): Boolean {
        var isFavorited: Boolean = false
        view.getContext().database.use {
            val result = select(Event.TABEL_FAVORITE_MATCH)
                    .whereArgs("(${Event.EVENT_ID} = {EVENT_ID})",
                            "EVENT_ID" to eventId)
            val favorite = result.parseList(classParser<Event>())
            if (!favorite.isEmpty())
                isFavorited = true
        }

        return isFavorited
    }

    override fun addToFavorite(event: Event?) {
        try {
            view.getContext().database.use {
                insert(Event.TABEL_FAVORITE_MATCH,
                        Event.EVENT_ID to event?.eventId,
                        Event.DATE to event?.date,
                        Event.TIME to event?.time,
                        Event.TEAM_HOME_ID to event?.teamHomeId,
                        Event.TEAM_HOME_NAME to event?.teamHomeName,
                        Event.TEAM_HOME_SCORE to event?.teamHomeScore,
                        Event.TEAM_HOME_GOAL_DETAILS to event?.teamHomeGoalDetails,
                        Event.TEAM_HOME_SHOTS to event?.teamHomeShots,
                        Event.TEAM_HOME_LINEUP_GOALKEEPER to event?.teamHomeLineupGoalkeeper,
                        Event.TEAM_HOME_LINEUP_DEFENSE to event?.teamHomeLineupDefense,
                        Event.TEAM_HOME_LINEUP_MIDFIELD to event?.teamHomeLineupMidfield,
                        Event.TEAM_HOME_LINEUP_FORWARD to event?.teamHomeLineupForward,
                        Event.TEAM_AWAY_ID to event?.teamAwayId,
                        Event.TEAM_AWAY_NAME to event?.teamAwayName,
                        Event.TEAM_AWAY_SCORE to event?.teamAwayScore,
                        Event.TEAM_AWAY_GOAL_DETAILS to event?.teamAwayGoalDetails,
                        Event.TEAM_AWAY_SHOTS to event?.teamAwayShots,
                        Event.TEAM_AWAY_LINEUP_GOALKEEPER to event?.teamAwayLineupGoalkeeper,
                        Event.TEAM_AWAY_LINEUP_DEFENSE to event?.teamAwayLineupDefense,
                        Event.TEAM_AWAY_LINEUP_MIDFIELD to event?.teamAwayLineupMidfield,
                        Event.TEAM_AWAY_LINEUP_FORWARD to event?.teamAwayLineupForward,
                        Event.SPORT_TYPE to event?.sportType)
            }
            Toast.makeText(view.getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(view.getContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeFromFavorite(eventId: String) {
        try {
            view.getContext().database.use {
                delete(Event.TABEL_FAVORITE_MATCH,
                        "(${Event.EVENT_ID} = {EVENT_ID})",
                        "EVENT_ID" to eventId)
            }
            Toast.makeText(view.getContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(view.getContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()

        }
    }
}