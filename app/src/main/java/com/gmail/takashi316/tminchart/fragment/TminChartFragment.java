package com.gmail.takashi316.tminchart.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.takashi316.tminchart.db.ConstantValues;
import com.gmail.takashi316.tminchart.view.Konoji;
import com.gmail.takashi316.tminchart.R;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final double MAX_GAP_INCH = 0.3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TableLayout tableLayout;
    private Random random = new Random();

    private OnFragmentInteractionListener mListener;
    private ArrayList<Konoji> konojiViews = new ArrayList<Konoji>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TminChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TminChartFragment newInstance(String param1, String param2) {
        TminChartFragment fragment = new TminChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

    public TminChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readSharedPreferences();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }//if
    }//onCreate

    Button buttonStartResultFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tmin_chart2, container, false);
        buttonStartResultFragment = (Button)view.findViewById(R.id.buttonStartResultFragment);
        buttonStartResultFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks)getActivity()).onNavigationDrawerItemSelected(3);
            }//onClick
        });

        tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTminChart);
        TableRow tr_index = new TableRow(getActivity());
        final int TEXT_SIZE = 50;
        TextView tv_tc = new TextView(getActivity());
        tv_tc.setTextSize(TEXT_SIZE);
        tv_tc.setText(" ");
        tr_index.addView(tv_tc);
        OnFragmentInteractionListener l = (OnFragmentInteractionListener)getActivity();
        final int width_pixels = (int) (tminChartMaxGapInch * 4 * l.getXdpi());
        final int COLUMNS = l.getWidthPixels()/width_pixels;
        final int ROWS = tminChartCount / COLUMNS;
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
                final double gap_inch = tminChartMaxGapInch *  Math.pow(tminChartRatio, x + y * COLUMNS);
                Konoji konoji = new Konoji(getActivity(), (float) gap_inch, (float) tminChartMaxGapInch * 4, konojiViews);
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

    // will be overridden by shared preferences
    private double tminChartMaxGapInch = ConstantValues.TMIN_CHART_MAX_GAP_INCH;
    private double tminChartRatio = ConstantValues.TMIN_CHART_RATIO;
    private int tminChartCount = ConstantValues.TMIN_CHART_COUNT;

    public void readSharedPreferences(){
            try{
                final SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity()) ;
                tminChartMaxGapInch = Double.parseDouble(shared_preferences.getString("tmin_chart_max_gap_inch", Double.toString(tminChartMaxGapInch)));
                tminChartRatio = Double.parseDouble(shared_preferences.getString("tmin_chart_ratio", Double.toString(tminChartRatio)));
                tminChartCount = Integer.parseInt(shared_preferences.getString("tmin_chart_count", Integer.toString(tminChartCount)));
            } catch (Exception e){
                e.printStackTrace();
            }//try
        }//readSharedPreferences

    @Override
    public String toString(){
        for(Konoji konoji: konojiViews){
            if(konoji.isTouched()){
                return Float.toString(konoji.getGapInch());
            }//if
        }//for
        return "未測定";
    }//toString
}//TminChartFragment

