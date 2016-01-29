package com.gmail.takashi316.tminchart.sandbox;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SandboxActivityTest {
    static final int WAIT_MILLS = 2000;
    SandboxActivity sandboxActivity;
    ViewGroup content, linearLayout;

    @Rule
    public ActivityTestRule<SandboxActivity> sandboxActivityTestRule = new ActivityTestRule<>(SandboxActivity.class);

    @Before
    public void before() {
        this.sandboxActivity = sandboxActivityTestRule.getActivity();
        this.content = (ViewGroup) sandboxActivity.findViewById(android.R.id.content);
        this.linearLayout = (LinearLayout) this.content.getChildAt(0);
    }

    @After
    public void after() {

    }

    @Test
    public void linearLayout() {
        Assert.assertNotNull(this.linearLayout);
        Assert.assertEquals(4, this.linearLayout.getChildCount());
    }

    @Test
    public void content() {
        Assert.assertNotNull(this.content);
        Assert.assertNotNull(this.content.findViewById(android.R.id.content + 11111111));
        Assert.assertNotNull(this.content.findViewById(android.R.id.content + 22222222));
        Assert.assertEquals(1, this.content.getChildCount());
    }

    @Test
    public void activity() {
        Assert.assertNotNull(this.sandboxActivity);
        Assert.assertNotNull(this.sandboxActivity.button1);
        Assert.assertNotNull(this.sandboxActivity.findViewById(android.R.id.content + 22222222));
    }

    @Test
    public void testWithId() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 11111111)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 22222222)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 33333333)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 44444444)).check(ViewAssertions.matches(ViewMatchers.withText("" + 3)));
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 11111111)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 22222222)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 33333333)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(android.R.id.content + 44444444)).check(ViewAssertions.matches(ViewMatchers.withText("" + 6)));
        Thread.sleep(WAIT_MILLS);
    }

    @Test
    public void testWithTagValue() throws InterruptedException {
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 1))).perform(ViewActions.click());
        try {
            Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 2))).perform(ViewActions.click());
        } catch (AmbiguousViewMatcherException e) {
        }
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 4))).check(ViewAssertions.matches(ViewMatchers.withText("" + 1)));
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 1))).perform(ViewActions.click());
        try {
            Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 2))).perform(ViewActions.click());
        } catch (AmbiguousViewMatcherException e) {
        }
        Espresso.onView(ViewMatchers.withTagValue(Matchers.is((Object) 4))).check(ViewAssertions.matches(ViewMatchers.withText("" + 2)));
        Thread.sleep(WAIT_MILLS);
    }

    @Test
    public void countViews() {
    }
}
