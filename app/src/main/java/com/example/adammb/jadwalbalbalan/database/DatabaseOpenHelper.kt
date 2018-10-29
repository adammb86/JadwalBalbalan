package com.example.adammb.jadwalbalbalan.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.CalendarContract.Instances.EVENT_ID
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.DATE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_GOAL_DETAILS
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_ID
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_LINEUP_DEFENSE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_LINEUP_FORWARD
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_LINEUP_GOALKEEPER
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_LINEUP_MIDFIELD
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_NAME
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_SCORE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_AWAY_SHOTS
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_GOAL_DETAILS
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_ID
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_LINEUP_DEFENSE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_LINEUP_FORWARD
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_LINEUP_GOALKEEPER
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_LINEUP_MIDFIELD
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_NAME
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_SCORE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TEAM_HOME_SHOTS
import com.example.adammb.jadwalbalbalan.model.team.Team
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "db_jadwalbalbalan.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(context.applicationContext)
            }

            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Event.TABEL_FAVORITE_MATCH, true,
                BaseColumns._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EVENT_ID to TEXT,
                DATE to TEXT,
                Event.TIME to TEXT,
                TEAM_HOME_ID to TEXT,
                TEAM_HOME_NAME to TEXT,
                TEAM_HOME_SCORE to TEXT,
                TEAM_HOME_GOAL_DETAILS to TEXT,
                TEAM_HOME_SHOTS to TEXT,
                TEAM_HOME_LINEUP_GOALKEEPER to TEXT,
                TEAM_HOME_LINEUP_DEFENSE to TEXT,
                TEAM_HOME_LINEUP_MIDFIELD to TEXT,
                TEAM_HOME_LINEUP_FORWARD to TEXT,
                TEAM_AWAY_ID to TEXT,
                TEAM_AWAY_NAME to TEXT,
                TEAM_AWAY_SCORE to TEXT,
                TEAM_AWAY_GOAL_DETAILS to TEXT,
                TEAM_AWAY_SHOTS to TEXT,
                TEAM_AWAY_LINEUP_GOALKEEPER to TEXT,
                TEAM_AWAY_LINEUP_DEFENSE to TEXT,
                TEAM_AWAY_LINEUP_MIDFIELD to TEXT,
                TEAM_AWAY_LINEUP_FORWARD to TEXT,
                Event.SPORT_TYPE to TEXT)


        db.createTable(Team.TABEL_FAVORITE_TEAM, true,
                BaseColumns._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.TEAM_ID to TEXT,
                Team.TEAM_NAME to TEXT,
                Team.TEAM_BADGE to TEXT,
                Team.TEAM_STADIUM to TEXT,
                Team.TEAM_FORMED_YEAR to TEXT,
                Team.TEAM_DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Event.TABEL_FAVORITE_MATCH, true)
        db.dropTable(Team.TABEL_FAVORITE_TEAM, true)
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)