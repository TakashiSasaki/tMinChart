package com.gmail.takashi316.tminchart;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Point;

import java.lang.reflect.Method;
import java.nio.DoubleBuffer;

import com.gmail.takashi316.tminchart.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayPropertyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayPropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DisplayPropertyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText editTextLightSensor;
    private EditText editTextAccelerometerX;
    private EditText getEditTextAccelerometerY;
    private EditText getEditTextAccelerometerZ;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayPropertyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayPropertyFragment newInstance(String param1, String param2) {
        DisplayPropertyFragment fragment = new DisplayPropertyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public DisplayPropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_property, container, false);
        editTextLightSensor = (EditText) view.findViewById(R.id.editTextLightSensor);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public int getDecorViewWidth();
        public int getDecorViewHeight();
        public float getLightSensorValue();
        public float getAccelerometerX();
        public float getAccelerometerY();
        public float getAccelerometerZ();
    }

    @Override
    public void onResume() {
        super.onResume();
        final View view = this.getView();
        final OnFragmentInteractionListener activity = (OnFragmentInteractionListener) this.getActivity();

        //LCD size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point real = new Point(0,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            display.getRealSize(real);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            try {
                Method getRawWidth = Display.class.getMethod("getRawWidth");
                Method getRawHeight = Display.class.getMethod("getRawHeight");
                final int width = (Integer) getRawWidth.invoke(display);
                final int height = (Integer) getRawHeight.invoke(display);
                real.set(width, height);
            } catch (Exception e){
                e.printStackTrace();
            }//try
        }//if

        {
            final EditText w = (EditText) view.findViewById(R.id.editTextRawWidth);
            w.setText(Integer.toString(real.x));
            final EditText h = (EditText) view.findViewById(R.id.editTextRawHeight);
            h.setText(Integer.toString(real.y));
        }
        {
            Point point = new Point(0,0);
            display.getSize(point);
            final EditText x = (EditText) view.findViewById(R.id.editTextDisplaySizeX);
            x.setText(Integer.toString(point.x));
            final EditText y = (EditText) view.findViewById(R.id.editTextDisplaySizeY);
            y.setText(Integer.toString(point.y));
        }
        {
            final EditText x = (EditText) view.findViewById(R.id.editTextViewWidth);
            x.setText(Integer.toString(activity.getDecorViewWidth()));
            final EditText y = (EditText) view.findViewById(R.id.editTextViewHeight);
            y.setText(Integer.toString(activity.getDecorViewHeight()));
        }
        {
            final DisplayMetrics display_metrics = new DisplayMetrics();
            display.getMetrics(display_metrics);
            ((EditText)view.findViewById(R.id.editTextDensity)).setText(Float.toString(display_metrics.density));
            ((EditText)view.findViewById(R.id.editTextDensityDpi)).setText(Integer.toString(display_metrics.densityDpi));
            ((EditText)view.findViewById(R.id.editTextHeightPixels)).setText(Integer.toString(display_metrics.heightPixels));
            ((EditText)view.findViewById(R.id.editTextWidthPixels)).setText(Integer.toString(display_metrics.widthPixels));
            ((EditText)view.findViewById(R.id.editTextXDpi)).setText(Float.toString(display_metrics.xdpi));
            ((EditText)view.findViewById(R.id.editTextYDpi)).setText(Float.toString(display_metrics.ydpi));
            ((EditText)view.findViewById(R.id.editTextScaledDensity)).setText(Float.toString(display_metrics.scaledDensity));
        }
        editTextLightSensor.setText(Float.toString(activity.getLightSensorValue()));
    }//onResume
}
