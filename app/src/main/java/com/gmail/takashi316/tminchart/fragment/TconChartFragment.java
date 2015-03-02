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
    private SharedPreferences preferences;
    private TableLayout tableLayout;
    private int nStrokes;
    private int nRows, nColumns;
    private double maxInch;
    private double sizeRatio;
    private double contrastRatio;
    Button buttonStartTminChartFragment;
    //private int tconChartRows = ConstantValues.TCON_CHART_ROWS;
    //private double tconChartMaxInch = ConstantValues.TCON_CHART_MAX_INCH;
    //private double tconChartSizeRatio = ConstantValues.TCON_CHART_SIZE_RATIO;
    //private double tconCharContrastRatio = ConstantValues.TCON_CHART_CONTRAST_RATIO;
    //private int tconChartColumns = ConstantValues.TCON_CHART_COLUMNS;

    private OnFragmentInteractionListener mListener;
    ArrayList<ArrayList<Seventeen>> seventeenArrayLists = new ArrayList<ArrayList<Seventeen>>();

    public static TconChartFragment newInstance(int n_strokes, int n_rows, int n_columns, double max_inch,
                                                double size_ratio, double contrast_ratio) {
        TconChartFragment fragment = new TconChartFragment();
        Bundle args = new Bundle();
        args.putInt("nStrokes", n_strokes);
        args.putInt("nRows", n_rows);
        args.putInt("nColumns", n_columns);
        args.putDouble("maxInch", max_inch);
        args.putDouble("sizeRatio", size_ratio);
        args.putDouble("contrastRatio", contrast_ratio);
        fragment.setArguments(args);
        return fragment;
    }//newInstance

    public TconChartFragment() {
        // Required empty public constructor
    }//TconChartFragment

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.nStrokes = args.getInt("nStrokes");
        this.nRows = args.getInt("nRows");
        this.nColumns = args.getInt("nColumns");
        this.maxInch = args.getDouble("maxInch");
        this.sizeRatio = args.getDouble("sizeRatio");
        this.contrastRatio = args.getDouble("contrastRatio");
    }//setArguments

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readSharedPreferences();
    }//onCreate


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        OnFragmentInteractionListener l = (OnFragmentInteractionListener) getActivity();
        View view = inflater.inflate(R.layout.fragment_tcon_chart, container, false);
        this.buttonStartTminChartFragment = (Button) view.findViewById(R.id.buttonStartTminChartFragment);
        this.buttonStartTminChartFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks) getActivity()).onNavigationDrawerItemSelected(2);
            }//onClick
        });

        this.tableLayout = (TableLayout) view.findViewById(R.id.tableLayoutTconChart);
        this.seventeenArrayLists.clear();
        for (int y = 0; y < this.nRows; ++y) {
            ArrayList<Seventeen> seventeens = new ArrayList<Seventeen>();
            seventeenArrayLists.add(seventeens);
            double width_inch = this.maxInch * Math.pow(this.sizeRatio, y);
            Seventeen leftmost = new Seventeen(getActivity(), this.maxInch, -1.0, seventeens, "×");
            leftmost.setMinimumHeight((int) ((this.maxInch + MARGIN_INCH) * l.getYdpi()));
            leftmost.setMinimumWidth((int) ((this.maxInch + MARGIN_INCH) * l.getXdpi()));
            TableRow table_row = new TableRow(getActivity());
            table_row.addView(leftmost);
            seventeens.add(leftmost);
            for (int x = 0; x < this.nColumns; ++x) {
                TextView text_view = new TextView(getActivity());
                text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.RED);
                    }
                });
                final double intention = 255.0 - (255.0 * Math.pow(this.contrastRatio, x));
                Seventeen seventeen = new Seventeen(getActivity(), width_inch, intention, seventeens, null);
                seventeen.setMinimumHeight((int) ((this.maxInch + MARGIN_INCH) * l.getYdpi()));
                seventeen.setMinimumWidth((int) ((this.maxInch + MARGIN_INCH) * l.getXdpi()));
                table_row.addView(seventeen);
                seventeens.add(seventeen);
            }//for y
            tableLayout.addView(table_row);
        }//for x

        return view;
    }//onCreateView

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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);

        public float getXdpi();

        public float getYdpi();

        public int getWidthPixels();
    }//OnFragmentInteractionListener

    // will be overridden by shared preferences

    private void readSharedPreferences() {
        try {
            final SharedPreferences shared_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            //tconChartMaxInch = Double.parseDouble(shared_preferences.getString("tcon_chart_max_inch", Double.toString(tconChartMaxInch)));
            //tconChartSizeRatio = Double.parseDouble(shared_preferences.getString("tcon_chart_size_ratio", Double.toString(tconChartSizeRatio)));
            //tconChartRows = Integer.parseInt(shared_preferences.getString("tcon_chart_rows", Integer.toString(tconChartRows)));
            //tconCharContrastRatio = Double.parseDouble(shared_preferences.getString("tcon_chart_contrast_ratio", Double.toString(tconCharContrastRatio)));
            //tconChartColumns = Integer.parseInt(shared_preferences.getString("tcon_chart_columns", Integer.toString(tconChartColumns)));
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

