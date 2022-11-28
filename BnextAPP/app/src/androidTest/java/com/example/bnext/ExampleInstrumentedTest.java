package com.example.bnext;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import model.User;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.bnext", appContext.getPackageName());
    }

    @Test
        public void loginButton() {

        onView(withId(R.id.loginButton))
                //.perform(click())
                .check(matches(isDisplayed()));

    }

    @Test
    public void inputEmail() {

        onView(withId(R.id.inputEmail))
                //.perform(click())
                .check(matches(isDisplayed()));

    }
    @Test
    public void inputPassword() {

        onView(withId(R.id.inputPassword))
                //.perform(click())
                .check(matches(isDisplayed()));

    }
    @Test
    public void passwordText() {

        onView(withId(R.id.passwordText))
                //.perform(click())
                .check(matches(isDisplayed()));

    }
    @Test
    public void emailText() {

        onView(withId(R.id.emailText))
                //.perform(click())
                .check(matches(isDisplayed()));

    }

    @Test
    public void signUpButton() {

        onView(withId(R.id.signUpButton))
                //.perform(click())
                .check(matches(isDisplayed()));

    }
    @Test
    public void infoText() {

        onView(withId(R.id.infoText))
                //.perform(click())
                .check(matches(isDisplayed()));

    }

}