package com.gmail.takashi316.tminchart.stripe;

import android.os.StrictMode;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.gmail.takashi316.tminchart.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HorizontalBlackOnWhiteActivityTest {
    @Rule
    public ActivityTestRule<HorizontalBlackOnWhite> activityTestRule = new ActivityTestRule<>(HorizontalBlackOnWhite.class);

    @Before
    public void init() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
    }

    @Test
    public void createActivity() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.buttonStart)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeUp());
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeLeft());
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeLeft());
        Thread.sleep(1000);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeRight());
        Thread.sleep(1000);
    }
}
