package com.gmail.takashi316.tminchart.conchart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

public class ConChartActivity extends Activity implements ConChartFragment.OnFragmentInteractionListener {

    private final int DEFAULT_STROKES = 17;
    private final int DEFAULT_ROWS = 20;
    private final int DEFAULT_COLUMNS = 20;
    private final double DEFAULT_MAX_INCH =  30.0 / 72.0;
    private final double DEFAULT_SIZE_RATIO = Math.pow(0.1, 0.01);
    private final double DEFAULT_CONTRAST_RATIO = Math.pow(0.1, 0.01);
    private final Typeface DEFAULT_TYPEFACE = Typeface.DEFAULT;

    private DisplayDpi displayDpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_chart);
        this.getActionBar().hide();

        this.displayDpi = new DisplayDpi(this);
        Fragment fragment = ConChartFragment.newInstance(DEFAULT_STROKES, DEFAULT_ROWS,
                DEFAULT_COLUMNS, DEFAULT_MAX_INCH, DEFAULT_SIZE_RATIO, DEFAULT_CONTRAST_RATIO, DEFAULT_TYPEFACE);
        FragmentManager fragmentManager = this.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public float getXdpi() {
        return displayDpi.getXdpi();
    }

    @Override
    public float getYdpi() {
        return displayDpi.getYdpi();
    }

    @Override
    public int getWidthPixels() {
        return displayDpi.getWidthPixels();
    }
}
