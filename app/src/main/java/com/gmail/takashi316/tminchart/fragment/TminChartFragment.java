package com.gmail.takashi316.tminchart.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.takashi316.tminchart.DisplayDpi;
import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.minchart.MinChartParams;
import com.gmail.takashi316.tminchart.view.Konoji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TminChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TminChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class TminChartFragment extends Fragment {
    private MinChartParams params;
    private TableLayout tableLayout;
    private Random random = new Random();
    static private ObjectMapper objectMapper = new ObjectMapper();

    private OnFragmentInteractionListener mListener;
    private ArrayList<Konoji> konojiViews = new ArrayList<Konoji>();
    private DisplayDpi display;

    public static TminChartFragment newInstance(MinChartParams params) throws JsonProcessingException {
        TminChartFragment fragment = new TminChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("params", objectMapper.writeValueAsString(params));
        fragment.setArguments(bundle);
        return fragment;
    }//newInstance

    public TminChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.params = objectMapper.readValue(getArguments().getString("params"), MinChartParams.class);
        } catch (IOException e) {
            this.params = new MinChartParams();
        }
    }//onCreate

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    Button buttonStartResultFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tmin_chart2, container, false);
        buttonStartResultFragment = (Button) view.findViewById(R.id.buttonStartResultFragment);
        buttonStartResultFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks) getActivity()).onNavigationDrawerItemSelected(3);
            }//onClick
        });

        tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTminChart);
        TableRow tr_index = new TableRow(getActivity());
        final int TEXT_SIZE = 50;
        TextView tv_tc = new TextView(getActivity());
        tv_tc.setTextSize(TEXT_SIZE);
        tv_tc.setText(" ");
        tr_index.addView(tv_tc);
        OnFragmentInteractionListener l = (OnFragmentInteractionListener) getActivity();
        final int width_pixels = (int) (params.tminChartMaxGapInch * 4 * l.getXdpi());
        final int COLUMNS = l.getWidthPixels() / width_pixels;
        final int ROWS = params.tminChartCount / COLUMNS;
        for (int x = 1; x <= COLUMNS; ++x) {
            TextView text_view = new TextView(getActivity());
            text_view.setTextSize(TEXT_SIZE);
            text_view.setText(new String(new byte[]{(byte) (x + 64)}));
            text_view.setGravity(Gravity.CENTER);
            tr_index.addView(text_view);
        }//for
        tableLayout.addView(tr_index);
        for (int y = 0; y < ROWS; ++y) {
            TableRow table_row = new TableRow(getActivity());
            TextView tv_index = new TextView(getActivity());
            tv_index.setTextSize(TEXT_SIZE);
            tv_index.setText(Integer.toString(y));
            tv_index.setGravity(Gravity.CENTER);
            table_row.addView(tv_index);
            for (int x = 0; x < COLUMNS; ++x) {
                final double gap_inch = params.tminChartMaxGapInch * Math.pow(params.tminChartRatio, x + y * COLUMNS);
                Konoji konoji = new Konoji(getActivity(), (float) gap_inch, (float) params.tminChartMaxGapInch * 4, konojiViews);
                konojiViews.add(konoji);
                table_row.addView(konoji);
            }//for y
            tableLayout.addView(table_row);
        }//for x
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }//if
    }//onButtonPressed

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.display = new DisplayDpi(activity);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);

        public float getXdpi();

        public float getYdpi();

        public int getWidthPixels();
    }//OnFragmentInteractionListener


    @Override
    public String toString() {
        for (Konoji konoji : konojiViews) {
            if (konoji.isTouched()) {
                return Float.toString(konoji.getGapInch());
            }//if
        }//for
        return "未測定";
    }//toString
}//TminChartFragment

