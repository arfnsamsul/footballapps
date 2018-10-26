package com.samsularifin.footballclub

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.samsularifin.footballclub.R.id.*
import com.samsularifin.footballclub.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    /*@Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(5000)
        onView(withId(list_team))
                .check(matches(isDisplayed()))
        onView(withId(list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(list_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(5000)
    }*/

    @Test
    fun testAppBehaviour() {
        Thread.sleep(5000)
        onView(withId(R.id.league_spinner))
                .check(matches(isDisplayed()))
        onView(withId(R.id.league_spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(5000)

        onView(withText("Barcelona"))
                .check(matches(isDisplayed()))
        onView(withText("Barcelona")).perform(click())

        Thread.sleep(1000)

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()
        Thread.sleep(1000)

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
        Thread.sleep(5000)
    }
}