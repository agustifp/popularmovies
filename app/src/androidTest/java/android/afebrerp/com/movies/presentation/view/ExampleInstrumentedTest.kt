package com.afebrerp.movies.android

import android.afebrerp.com.movies.presentation.view.splash.SplashActivity
import android.view.View
import android.view.ViewGroup
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.afebrerp.movies.android.testExtensions.performClick
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun testUseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(3000)

        val actionMenuItemView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.action_search), ViewMatchers.withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.toolbar),
                                        1),
                                0),
                        ViewMatchers.isDisplayed()))
        actionMenuItemView.performClick()
        Thread.sleep(2000)

        onView(ViewMatchers.withId(R.id.search_view))

                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("Pulp fiction"))
                .perform(pressImeActionButton())
                .check(matches(Matchers.not(hasFocus())))

        closeSoftKeyboard()
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
