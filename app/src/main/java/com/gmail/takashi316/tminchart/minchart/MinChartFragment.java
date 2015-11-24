package com.gmail.takashi316.tminchart.minchart;

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
import com.gmail.takashi316.tminchart.fragment.NavigationDrawerFragment;

import java.io.IOException;
import java.util.ArrayList;

public class MinChartFragment extends Fragment {
    private MinChartParams params;
    private TableLayout tableLayout;
    static private ObjectMapper objectMapper = new ObjectMapper();

    private OnFragmentInteractionListener mListener;
    private ArrayList<Konoji> konojiViews = new ArrayList<Konoji>();
    private DisplayDpi display;

    public static MinChartFragment newInstance(MinChartParams params) throws JsonProcessingException {
        MinChartFragment fragment = new MinChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("params", objectMapper.writeValueAsString(params));
        fragment.setArguments(bundle);
        return fragment;
    }//newInstance

    public MinChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.params = objectMapper.readValue(this.getArguments().getString("params"), MinChartParams.class);
        } catch (IOException e) {
            this.params = new MinChartParams();
        } catch (NullPointerException e) {
            this.params = new MinChartParams();
        }
    }//onCreate

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private Button buttonStartResultFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tmin_chart2, container, false);
        this.buttonStartResultFragment = (Button) view.findViewById(R.id.buttonStartResultFragment);
        this.buttonStartResultFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks) MinChartFragment.this.getActivity()).onNavigationDrawerItemSelected(3);
            }//onClick
        });

        this.tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTminChart);
        TableRow tr_index = new TableRow(this.getActivity());
        final int TEXT_SIZE = 50;
        tr_index.addView(new HeaderLabelView(this.getActivity(), "m"));
        //OnFragmentInteractionListener l = (OnFragmentInteractionListener) getActivity();
        final int width_pixels = (int) (this.params.minChartMaxGapInch * 4 * this.display.getXdpi()) + 20;
        final int COLUMNS = this.display.getWidthPixels() / width_pixels;
        final int ROWS = this.params.minChartCount / COLUMNS;
        for (int x = 1; x <= COLUMNS; ++x) {
            TextView text_view = new TextView(this.getActivity());
            text_view.setTextSize(TEXT_SIZE);
            text_view.setText(new String(new byte[]{(byte) (x + 64)}));
            text_view.setGravity(Gravity.CENTER);
            tr_index.addView(text_view);
        }//for
        this.tableLayout.addView(tr_index);
        for (int y = 0; y < ROWS; ++y) {
            TableRow table_row = new TableRow(this.getActivity());
            TextView tv_index = new TextView(this.getActivity());
            tv_index.setTextSize(TEXT_SIZE);
            tv_index.setText(Integer.toString(y));
            tv_index.setGravity(Gravity.CENTER);
            table_row.addView(tv_index);
            for (int x = 0; x < COLUMNS; ++x) {
                final double gap_inch = this.params.minChartMaxGapInch * Math.pow(this.params.minChartRatio, x + y * COLUMNS);
                Konoji konoji = new Konoji(this.getActivity(), (float) gap_inch, (float) this.params.minChartMaxGapInch * 4, this.konojiViews);
                this.konojiViews.add(konoji);
                table_row.addView(konoji);
            }//for y
            this.tableLayout.addView(table_row);
        }//for x
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }//if
    }//onButtonPressed

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.display = new DisplayDpi(activity);

        try {
            this.mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }//try
    }//onAttach

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }//onDetach

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }//OnFragmentInteractionListener
}//TminChartFragment
