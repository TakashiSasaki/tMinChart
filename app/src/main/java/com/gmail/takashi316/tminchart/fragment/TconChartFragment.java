package com.gmail.takashi316.tminchart.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.takashi316.tminchart.db.ConstantValues;
import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.view.Seventeen;

import java.io.StringWriter;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TconChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TconChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TconChartFragment extends Fragment {
    final static double MARGIN_INCH = 0.1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TableLayout tableLayout;

    private OnFragmentInteractionListener mListener;
    ArrayList<ArrayList<Seventeen>> seventeenArrayLists = new ArrayList<ArrayList<Seventeen>>();

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
        readSharedPreferences();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button buttonStartTminChartFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        OnFragmentInteractionListener l = (OnFragmentInteractionListener) getActivity();
        View view = inflater.inflate(R.layout.fragment_tcon_chart, container, false);
        buttonStartTminChartFragment = (Button) view.findViewById(R.id.buttonStartTminChartFragment);
        buttonStartTminChartFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks) getActivity()).onNavigationDrawerItemSelected(2);
            }//onClick
        });

        tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTconChart);
        seventeenArrayLists.clear();
        for (int y = 0; y < tconChartRows; ++y) {
            ArrayList<Seventeen> seventeens = new ArrayList<Seventeen>();
            seventeenArrayLists.add(seventeens);
            double width_inch = tconChartMaxInch * Math.pow(tconChartSizeRatio, y);
            Seventeen leftmost = new Seventeen(getActivity(), tconChartMaxInch, -1.0, seventeens, "×");
            leftmost.setMinimumHeight((int) ((tconChartMaxInch + MARGIN_INCH) * l.getYdpi()));
            leftmost.setMinimumWidth((int) ((tconChartMaxInch + MARGIN_INCH) * l.getXdpi()));
            TableRow table_row = new TableRow(getActivity());
            table_row.addView(leftmost);
            seventeens.add(leftmost);
            for (int x = 0; x < tconChartColumns; ++x) {
                TextView text_view = new TextView(getActivity());
                text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.RED);
                    }
                });
                final double intention = 255.0 - (255.0 * Math.pow(tconCharContrastRatio, x));
                Seventeen seventeen = new Seventeen(getActivity(), width_inch, intention, seventeens, null);
                seventeen.setMinimumHeight((int) ((tconChartMaxInch + MARGIN_INCH) * l.getYdpi()));
                seventeen.setMinimumWidth((int) ((tconChartMaxInch + MARGIN_INCH) * l.getXdpi()));
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);

        public float getXdpi();

        public float getYdpi();

        public int getWidthPixels();
    }//OnFragmentInteractionListener

    // will be overridden by shared preferences
    private double tconChartMaxInch = ConstantValues.TCON_CHART_MAX_INCH;
    private double tconChartSizeRatio = ConstantValues.TCON_CHART_SIZE_RATIO;
    private int tconChartRows = ConstantValues.TCON_CHART_ROWS;
    private double tconCharContrastRatio = ConstantValues.TCON_CHART_CONTRAST_RATIO;
    private int tconChartColumns = ConstantValues.TCON_CHART_COLUMNS;

    private void readSharedPreferences() {
        try {
            final SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            tconChartMaxInch = Double.parseDouble(shared_preferences.getString("tcon_chart_max_inch", Double.toString(tconChartMaxInch)));
            tconChartSizeRatio = Double.parseDouble(shared_preferences.getString("tcon_chart_size_ratio", Double.toString(tconChartSizeRatio)));
            tconChartRows = Integer.parseInt(shared_preferences.getString("tcon_chart_rows", Integer.toString(tconChartRows)));
            tconCharContrastRatio = Double.parseDouble(shared_preferences.getString("tcon_chart_contrast_ratio", Double.toString(tconCharContrastRatio)));
            tconChartColumns = Integer.parseInt(shared_preferences.getString("tcon_chart_columns", Integer.toString(tconChartColumns)));
        } catch (Exception e) {
            e.printStackTrace();
        }//try
    }//readSharedPreferences

    @Override
    public String toString() {
        StringWriter string_writer = new StringWriter();
        for (ArrayList<Seventeen> seventeens : seventeenArrayLists) {
            for (Seventeen seventeen : seventeens) {
                if (seventeen.isTouched()) {
                    string_writer.write(seventeen.toString());
                    string_writer.write(" ");
                }//if
            }//for
        }//for
        return string_writer.getBuffer().toString();
    }//toString
}//TconChartFragment

