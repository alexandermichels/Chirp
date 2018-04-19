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

public class CreateChirpUITests
{
    @Rule
    public ActivityTestRule<CreateChirpActivity> mainActivity = new ActivityTestRule<CreateChirpActivity>(CreateChirpActivity.class);


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
    public void chirpTextBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.createChirp_chirpMessage)).perform(ViewActions.typeText("This project is a pain in the ass")).check(matches(isDisplayed()));
    }
}
