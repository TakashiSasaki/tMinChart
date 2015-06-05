package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.gmail.takashi316.tminchart.R;

public class StripeFragmentFactory {
    public static StripeFragment createWhiteBackgroundToBlackStripe(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).arithmetic(Color.WHITE, Color.BLACK));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).white());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment stripeFragment = new StripeFragment();
        stripeFragment.setArguments(bundle);
        return stripeFragment;
    }//createWhiteBackgroundBlackStripe


    public static StripeFragment createWhiteBackgroundFromBlackStripe(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).arithmetic(Color.BLACK, Color.WHITE));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).white());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment fragment = new StripeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static StripeFragment createBlackBackgroundFromWhiteStripe(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).arithmetic(Color.WHITE, Color.BLACK));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).black());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment fragment = new StripeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }//createBlackBackgroundFromWhiteStripe

    public static StripeFragment createBlackBackgroundToWhiteStripe(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).arithmetic(Color.BLACK, Color.WHITE));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).black());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment fragment = new StripeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }//createBlackBackgroundToWhiteStripe

    public static StripeFragment createWhiteBackground(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).decrement(Color.WHITE, Color.rgb(1, 1, 1)));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).white());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment stripeFragment = new StripeFragment();
        stripeFragment.setArguments(bundle);
        return stripeFragment;
    }//createWhiteBackground

    public static StripeFragment createBlackBackground(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).increment(Color.BLACK, Color.rgb(1, 1, 1)));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).black());
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment stripeFragment = new StripeFragment();
        stripeFragment.setArguments(bundle);
        return stripeFragment;
    }//createBlackBackground

    public static StripeFragment createGrayBackgroundToBlack(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).increment(Color.rgb(128, 128, 128), Color.rgb(1, 1, 1)));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).color(Color.rgb(128, 128, 128)));
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment stripeFragment = new StripeFragment();
        stripeFragment.setArguments(bundle);
        return stripeFragment;
    }//createBlackBackground

    public static StripeFragment createGrayBackgroundToWhite(Context context) {
        final int rows = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_ROWS);
        final int columns = context.getResources().getInteger(R.integer.STRIPE_FRAGMENT_COLUMNS);
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", rows);
        bundle.putInt("nTableColumns", columns);
        bundle.putInt("nSteps", columns - 1);
        bundle.putIntArray("foregroundColorSequence", (new ColorSequence(columns)).increment(Color.rgb(128, 128, 128), Color.rgb(1, 1, 1)));
        bundle.putIntArray("backgroundColorSequence", (new ColorSequence(columns)).color(Color.rgb(128, 128, 128)));
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, rows - 1, true));
        bundle.putInt("frameWidth", 10);
        bundle.putInt("viewMargin", 10);
        StripeFragment stripeFragment = new StripeFragment();
        stripeFragment.setArguments(bundle);
        return stripeFragment;
    }//createBlackBackground
    
}//StripeFragmentFactory
