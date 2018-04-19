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
        onView(ViewMatchers.withId(R.id.loginActivity_UserEmail)).perform(ViewActions.typeText("test@example.com")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void userPasswordTexBoxWorks()
    {
        onView(ViewMatchers.withId(R.id.loginActivity_UserPassword)).perform(ViewActions.typeText("password")).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void loginButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.loginActivity_LoginButton)).perform(ViewActions.click());
    }

    @Test
    public void registerButtonWorks()
    {
        onView(ViewMatchers.withId(R.id.loginActivity_RegisterButton)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.registerActivity_confirmPassword)).check(matches(isDisplayed()));
    }
}
