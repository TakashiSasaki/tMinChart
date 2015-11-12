package com.gmail.takashi316.tminchart.location;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.takashi316.tminchart.R;

import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoationThreadTestActivityFragment extends Fragment {
    private Location location;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextAddress;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextJson;

    public LoationThreadTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_loation_thread_test, container, false);
        this.editTextAddress = (EditText) view.findViewById(R.id.editTextAddress);
        this.editTextLatitude = (EditText) view.findViewById(R.id.editTextLatitude);
        this.editTextLongitude = (EditText) view.findViewById(R.id.editTextLongitude);
        this.editTextDate = (EditText) view.findViewById(R.id.editTextDate);
        this.editTextTime = (EditText) view.findViewById(R.id.editTextTime);
        this.editTextJson = (EditText) view.findViewById(R.id.editTextJson);

        this.location = new Location(getActivity().getApplicationContext());
        this.location.setCallback(new Runnable() {
            @Override
            public void run() {
                if (location.address != null) {
                    editTextAddress.setText(location.address);
                }
                editTextLongitude.setText(Double.toString(location.longitude));
                editTextLatitude.setText(Double.toString(location.latitude));
                Date date = new Date();
                editTextDate.setText(date.toString());
                editTextTime.setText(date.toString());
                editTextJson.setText(location.getJson());
            }
        });

        return view;
    }
}
