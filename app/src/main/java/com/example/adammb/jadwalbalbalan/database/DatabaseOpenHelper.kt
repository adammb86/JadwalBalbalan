package com.example.adammb.jadwalbalbalan.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build.ID
import android.provider.CalendarContract.Instances.EVENT_ID
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.DATE
import com.example.adammb.jadwalbalbalan.model.event.Event.Favorite.TABLE_FAVORITE
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
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {
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
        db.createTable(TABLE_FAVORITE, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EVENT_ID to TEXT,
                DATE to TEXT,
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
                TEAM_AWAY_LINEUP_FORWARD to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TABLE_FAVORITE, true)
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)