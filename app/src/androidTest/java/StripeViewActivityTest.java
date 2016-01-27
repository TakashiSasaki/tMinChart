import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.stripe.StripeViewActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StripeViewActivityTest {
    @Rule
    public ActivityTestRule<StripeViewActivity> activityTestRule = new ActivityTestRule<>(StripeViewActivity.class);

    @Before
    public void init() {

    }

    @Test
    public void createView() {
        Espresso.onView(ViewMatchers.withId(R.id.viewStripe1)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.viewStripe2)).perform(ViewActions.click());
    }
}
