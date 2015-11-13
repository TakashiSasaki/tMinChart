package com.gmail.takashi316.tminchart.minchart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.fragment.TminChartFragment;

public class MinChartActivity extends Activity implements TminChartFragment.OnFragmentInteractionListener {

    DisplayDpi display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minchart);
        display = new DisplayDpi(this);

        Fragment fragment = null;
        try {
            fragment = TminChartFragment.newInstance(new MinChartParams());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }//onCreate

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
