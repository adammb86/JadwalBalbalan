package com.example.adammb.jadwalbalbalan.teamdetail

import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import com.example.adammb.jadwalbalbalan.database.database
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TABEL_FAVORITE_TEAM
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_BADGE
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_DESCRIPTION
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_FORMED_YEAR
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_ID
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_NAME
import com.example.adammb.jadwalbalbalan.model.team.Team.Favorite.TEAM_STADIUM
import com.example.adammb.jadwalbalbalan.util.CoroutineContextProvider
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailPresenter(private val view: TeamDetailContract.TeamDetailView,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) : TeamDetailContract.TeamDetailPresenter {


    override fun getFavoriteState(teamId: String): Boolean {
        var isFavorited: Boolean = false
        view.getContext().database.use {
            val result = select(TABEL_FAVORITE_TEAM)
                    .whereArgs("(${TEAM_ID} = {TEAM_ID})",
                            "TEAM_ID" to teamId)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty())
                isFavorited = true
        }

        return isFavorited
    }

    override fun addToFavorite(team: Team?) {
        try {
            view.getContext().database.use {
                insert(TABEL_FAVORITE_TEAM,
                        TEAM_ID to team?.teamId,
                        TEAM_NAME to team?.teamName,
                        TEAM_BADGE to team?.teamBadge,
                        TEAM_STADIUM to team?.teamStadium,
                        TEAM_FORMED_YEAR to team?.teamFormedYear,
                        TEAM_DESCRIPTION to team?.teamDescription)
            }
            Toast.makeText(view.getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(view.getContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeFromFavorite(teamId: String) {
        try {
            view.getContext().database.use {
                delete(TABEL_FAVORITE_TEAM,
                        "(${TEAM_ID} = {TEAM_ID})",
                        "TEAM_ID" to teamId)
            }
            Toast.makeText(view.getContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(view.getContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()

        }
    }
}