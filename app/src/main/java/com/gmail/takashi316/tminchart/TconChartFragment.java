package com.gmail.takashi316.tminchart;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TconChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TconChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TconChartFragment extends Fragment {
    final double MAX_WIDTH_INCH = 0.7;
    final int TEXT_SIZE_PIXELS = 50;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TableLayout tableLayout;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TconChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TconChartFragment newInstance(String param1, String param2) {
        TconChartFragment fragment = new TconChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public TconChartFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tcon_chart, container, false);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTconChart);
        TableRow tr_index = new TableRow(getActivity());
        TextView tv_tc  = new TextView(getActivity());
        tv_tc.setTextSize(TEXT_SIZE_PIXELS);
        tv_tc.setText("[c]");
        tr_index.addView(tv_tc);
        final int COLUMNS = 20;
        final int ROWS = 10;
        for(int x = 1; x <= COLUMNS; ++x ){
            TextView text_view = new TextView(getActivity());
            text_view.setTextSize(TEXT_SIZE_PIXELS);
            text_view.setGravity(Gravity.CENTER);
            text_view.setText(new String(new byte[]{(byte)(x+64)}));
            tr_index.addView(text_view);
        }//for
        tableLayout.addView(tr_index);
        for(int y = 0; y<ROWS; ++y) {
            TableRow table_row = new TableRow(getActivity());
            TextView tv_index = new TextView(getActivity());
            tv_index.setTextSize(TEXT_SIZE_PIXELS);
            tv_index.setText(Integer.toString(y));
            table_row.addView(tv_index);
            ArrayList<Seventeen> seventeens = new ArrayList<Seventeen>();
            for(int x=0; x<COLUMNS; ++x){
                TextView text_view = new TextView(getActivity());
                text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.RED);
                    }
                });
                final double width_inch = MAX_WIDTH_INCH / Math.pow(1.3, y);
                final double intention = 255.0 -  (255.0/Math.pow(1.3,x));
                Seventeen seventeen = new Seventeen(getActivity(), width_inch, intention, seventeens);
                seventeen.setMinimumHeight(200);
                seventeen.setMinimumWidth(200);
                table_row.addView(seventeen);
                seventeens.add(seventeen);
            }//for y
            tableLayout.addView(table_row);
        }//for x

        return view;
    }//onCreateView

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
    }

}
