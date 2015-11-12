package com.gmail.takashi316.tminchart.location;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.takashi316.tminchart.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoationThreadTestActivityFragment extends Fragment {
    private LocationThread locationThread;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextAddress;

    public LoationThreadTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_loation_thread_test, container, false);
        this.editTextAddress = (EditText) view.findViewById(R.id.editTextAddress);
        this.editTextLatitude = (EditText) view.findViewById(R.id.editTextLatitude);
        this.editTextLongitude = (EditText) view.findViewById(R.id.editTextLongitude);

        view.findViewById(R.id.buttonStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationThread != null) {
                    locationThread.stopRequestLocationUpdates();
                }//if
            }
        });

        view.findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationThread != null) {
                    locationThread.stopRequestLocationUpdates();
                }//if
                locationThread = new LocationThread(getActivity().getApplicationContext());
                locationThread.setCallback(new Runnable() {
                    @Override
                    public void run() {
                        editTextAddress.setText(locationThread.address);
                        editTextLongitude.setText(Double.toString(locationThread.longitude));
                        editTextLatitude.setText(Double.toString(locationThread.latitude));
                    }//run
                });
                locationThread.start();
            }//onClick
        });
        return view;
    }


}
