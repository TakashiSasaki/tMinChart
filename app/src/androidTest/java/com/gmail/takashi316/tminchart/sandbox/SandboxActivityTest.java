package com.gmail.takashi316.tminchart.sandbox;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SandboxActivityTest {
    static final int WAIT_MILLS = 2000;

    @Rule
    public ActivityTestRule<SandboxActivity> sandboxActivityTestRule = new ActivityTestRule<>(SandboxActivity.class);

    @Before
    public void before() {
    }

    @After
    public void after() {

    }

    @Test
    public void test() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 11111111)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 22222222)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 33333333)).check(ViewAssertions.matches(ViewMatchers.withText("" + 2)));
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 11111111)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 22222222)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 33333333)).check(ViewAssertions.matches(ViewMatchers.withText("" + 4)));
        Thread.sleep(WAIT_MILLS);
    }
}
