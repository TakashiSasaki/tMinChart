package com.gmail.takashi316.tminchart.stripe;

import android.app.Activity;
import android.os.Build;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.gmail.takashi316.tminchart.R;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HorizontalBlackOnWhiteActivityTest {

    static final int WAIT_MILLS = 400;
    Activity activity;
    TableLayout tableLayout;
    ScrollView scrollView;
    HorizontalScrollView horizontalScrollView;
    ArrayList<TableRow> tableRows = new ArrayList<TableRow>();
    ArrayList<StripeView> stripeViews = new ArrayList<StripeView>();

    @Rule
    public ActivityTestRule<HorizontalBlackOnWhite> activityTestRule = new ActivityTestRule<>(HorizontalBlackOnWhite.class);

    @Before
    public void init() {
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        this.activity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
    }

    void findViews() {
        this.scrollView = (ScrollView) activity.findViewById(R.id.scrollViewStripe);
        this.horizontalScrollView = (HorizontalScrollView) activity.findViewById(R.id.horizontalScrollView);
        this.tableLayout = (TableLayout) activity.findViewById(R.id.tableLayoutStripe);
        for (int i = 0; i < this.tableLayout.getChildCount(); ++i) {
            this.tableRows.add((TableRow) this.tableLayout.getChildAt(i));
        }
        for (TableRow table_row : this.tableRows) {
            for (int j = 0; j < table_row.getChildCount(); ++j) {
                try {
                    stripeViews.add((StripeView) table_row.getChildAt(j));
                } catch (ClassCastException e) {
                }
            }
        }
    }

    @Test
    public void testFindViews() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.buttonStart)).perform(ViewActions.click());
        Thread.sleep(WAIT_MILLS);
        this.findViews();
        Assert.assertTrue(this.tableLayout != null);
        Assert.assertTrue(this.tableRows.size() > 0);
    }

    @Test
    public void testCreateActivity() throws InterruptedException {
        Log.v(this.getClass().getSimpleName(), Build.TYPE);
        Assert.assertEquals("eng", Build.TYPE);
        Espresso.onView(ViewMatchers.withId(R.id.buttonStart)).perform(ViewActions.click());
        Thread.sleep(WAIT_MILLS);
        findViews();
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeLeft());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeLeft());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeRight());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeUp());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeUp());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withId(R.id.fragmentStripe)).perform(ViewActions.swipeDown());
        Thread.sleep(WAIT_MILLS);
        Espresso.onView(ViewMatchers.withClassName(CoreMatchers.endsWith("StripeView"))).perform(ViewActions.click());
        Assert.assertTrue(true);
    }
}
