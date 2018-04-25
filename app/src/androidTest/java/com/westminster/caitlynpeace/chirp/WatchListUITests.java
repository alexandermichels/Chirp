package com.westminster.caitlynpeace.chirp;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class WatchListUITests
{
    @Rule
    public ActivityTestRule<WatchListActivity> mainActivity = new ActivityTestRule<WatchListActivity>(WatchListActivity.class);


    @Before
    public void init()
    {
        mainActivity.getActivity().getSupportFragmentManager().beginTransaction();
    }

    public Activity getActivity()
    {
        return mainActivity.getActivity();
    }

    @Test
    public void timelineButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.watchlist_timeline_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.timeline_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void createChirpButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.watchlist_create_chirp_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.createChirp_chirp_photo)).check(matches(isDisplayed()));
    }

    @Test
    public void logoutButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.menu_logout_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.loginActivity_RegisterButton)).check(matches(isDisplayed()));
    }
}
