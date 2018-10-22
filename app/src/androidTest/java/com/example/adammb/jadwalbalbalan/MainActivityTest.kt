package com.example.adammb.jadwalbalbalan

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.adammb.jadwalbalbalan.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        val bottomNavigationViewNextMatch = onView(withId(R.id.navigation_next_match))
        bottomNavigationViewNextMatch.check(matches(isDisplayed()))
        bottomNavigationViewNextMatch.perform(click())

        Thread.sleep(2000)
        val recyclerViewNextMatch = onView(withId(R.id.event_recyclerview))
        recyclerViewNextMatch
                .check(matches(isDisplayed()))
        recyclerViewNextMatch
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        recyclerViewNextMatch
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(9, click()))

        val actionMenuItemView = onView(withId(R.id.menu_favorite))
        actionMenuItemView
                .check(matches(isDisplayed()))
        actionMenuItemView
                .perform(click())

        pressBack()
        val bottomNavigationViewFavorite = onView(withId(R.id.navigation_favorite))
        bottomNavigationViewFavorite.check(matches(isDisplayed()))
        bottomNavigationViewFavorite.perform(click())
    }
}