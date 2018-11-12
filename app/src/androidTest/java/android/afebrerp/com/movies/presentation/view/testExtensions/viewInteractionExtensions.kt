package com.afebrerp.movies.android.testExtensions

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matchers.not

fun ViewInteraction.checkIsDisplayed(): ViewInteraction =
        check(matches(isDisplayed()))

fun ViewInteraction.checkIsNotDisplayed(): ViewInteraction =
        check(matches(not(isDisplayed())))

fun ViewInteraction.performClick(): ViewInteraction =
        perform(click())