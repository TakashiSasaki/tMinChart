package com.gmail.takashi316.tminchart.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.takashi316.tminchart.R;

public class SettingsFragment extends PreferenceFragment {

    private OnFragmentInteractionListener mListener;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }//newInstance

    public SettingsFragment() {
    }//SettingsFragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_tmin_chart);
    }//onCreate

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }//try
    }//onAttach

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }//onDetach

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }//OnFragmentInteractionListener

}//SettingsFragment
