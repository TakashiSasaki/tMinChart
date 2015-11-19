package com.gmail.takashi316.tminchart.minchart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;

public class MinChartActivity extends Activity implements MinChartFragment.OnFragmentInteractionListener {

    DisplayDpi display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_minchart);
        this.display = new DisplayDpi(this);

        Fragment fragment = null;
        try {
            fragment = MinChartFragment.newInstance(new MinChartParams());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = this.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }//onCreate

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
