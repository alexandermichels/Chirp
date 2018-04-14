package com.westminster.caitlynpeace.chirp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class LoginUITests
{
    @Rule
    public ActivityTestRule<LoginActivity> mainActivity = new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @Before
    public void init()
    {
        mainActivity.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void userEmailTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.UserEmail)).perform(ViewActions.typeText("test@example.com")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void userPasswordTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.UserPassword)).perform(ViewActions.typeText("password")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void loginButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.LoginButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.timeline_recyclerview)).check(matches(isDisplayed()));
    }
}
