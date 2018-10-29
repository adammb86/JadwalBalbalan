package com.example.adammb.jadwalbalbalan

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.adammb.jadwalbalbalan.main.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
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
        val bottomNavigationViewTeam = onView(withId(R.id.navigation_team))
        bottomNavigationViewTeam.check(matches(isDisplayed()))
        bottomNavigationViewTeam.perform(click())

        Thread.sleep(2000)
        val recyclerViewTeam = onView(withId(R.id.team_recyclerview))
        recyclerViewTeam
                .check(matches(isDisplayed()))
        recyclerViewTeam
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        recyclerViewTeam
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

        val tabFavoriteTab = onView(Matchers.allOf(ViewMatchers.withContentDescription("Team"),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.tabFavoriteTab),
                                0),
                        1),
                isDisplayed()))
        tabFavoriteTab.perform(click())
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}