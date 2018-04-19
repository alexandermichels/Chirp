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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;

public class RegisterUITests
{
    @Rule
    public ActivityTestRule<RegisterActivity> mainActivity = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);


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
    public void userEmailTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.registerActivity_userEmail)).perform(ViewActions.typeText("test@example.com")).check(matches(isDisplayed()));
    }

    @Test
    public void userHandleTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.registerActivity_userHandle)).perform(ViewActions.typeText("test@example.com")).check(matches(isDisplayed()));
    }

    @Test
    public void userPasswordTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.registerActivity_userPassword)).perform(ViewActions.typeText("password")).check(matches(isDisplayed()));
    }

    @Test
    public void confirmUserPasswordTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.registerActivity_confirmUserPassword)).perform(ViewActions.typeText("test@example.com")).check(matches(isDisplayed()));
    }
}
