package com.gmail.takashi316.tminchart.suite;

import com.gmail.takashi316.tminchart.stripe.StripeFragmentActivityTest;
import com.gmail.takashi316.tminchart.stripe.StripeViewActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({StripeViewActivityTest.class, StripeFragmentActivityTest.class})
public class MyTestSuite {
}
