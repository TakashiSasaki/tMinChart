package com.gmail.takashi316.tminchart.location;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.takashi316.tminchart.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoationThreadTestActivityFragment extends Fragment {
    final int INTERVAL_MILLIS = 5000;
    final int COUNT = 10;

    LocationThread locationThread;

    public LoationThreadTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_loation_thread_test, container, false);

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
                locationThread = new LocationThread(getActivity().getApplicationContext(), INTERVAL_MILLIS, COUNT);
                locationThread.setEditTextAddress((EditText) view.findViewById(R.id.editTextAddress));
                locationThread.setEditTextLatitude((EditText) view.findViewById(R.id.editTextLatitude));
                locationThread.setEditTextLongitude((EditText) view.findViewById(R.id.editTextLongitude));
                locationThread.setEditTextCount((EditText) view.findViewById(R.id.editTextCount));
                locationThread.start();
            }//onClick
        });
        return view;
    }


}
