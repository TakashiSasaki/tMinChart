package com.gmail.takashi316.tminchart.stripe;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

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
    }

    @Test
    public void createActivity() {
    }
}
