package com.westminster.caitlynpeace.chirp;

import android.app.Activity;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class TimelineUITests
{

    @Rule
    public ActivityTestRule<ViewRecentChirpsActivity> mainActivity = new ActivityTestRule<ViewRecentChirpsActivity>(ViewRecentChirpsActivity.class);


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
    public void timelineIsVisible()
    {
        onView(ViewMatchers.withId(R.id.timeline_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void editWatchButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.timeline_edit_watch)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.watchlist_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void createChirpButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.timeline_create_chirp)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.createChirp_chirp_photo)).check(matches(isDisplayed()));
    }

    @Test
    public void logoutButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.menu_logout_button)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.loginActivity_RegisterButton)).check(matches(isDisplayed()));
    }
}
