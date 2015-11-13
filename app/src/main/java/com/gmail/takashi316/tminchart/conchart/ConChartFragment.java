package com.gmail.takashi316.tminchart.conchart;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.fragment.NavigationDrawerFragment;

import java.io.StringWriter;
import java.util.ArrayList;

public class ConChartFragment extends Fragment {
    final static double MARGIN_INCH = 0.1;
    private TableLayout tableLayout;
    private int nStrokes;
    private int nRows, nColumns;
    private double maxInch;
    private double sizeRatio;
    private double contrastRatio;
    Button buttonStartTminChartFragment;
    private Typeface typeface;

    private OnFragmentInteractionListener mListener;
    ArrayList<ArrayList<ConChartView>> seventeenArrayLists = new ArrayList<ArrayList<ConChartView>>();

    public static ConChartFragment newInstance(int n_strokes, int n_rows, int n_columns, double max_inch,
                                                double size_ratio, double contrast_ratio, Typeface typeface) {
        ConChartFragment fragment = new ConChartFragment();
        Bundle args = new Bundle();
        args.putInt("nStrokes", n_strokes);
        args.putInt("nRows", n_rows);
        args.putInt("nColumns", n_columns);
        args.putDouble("maxInch", max_inch);
        args.putDouble("sizeRatio", size_ratio);
        args.putDouble("contrastRatio", contrast_ratio);
        args.putInt("fontStyle", typeface.getStyle());
        args.putString("fontFamily", typeface.toString());
        fragment.setArguments(args);
        return fragment;
    }//newInstance

    public ConChartFragment() {
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
        this.typeface = Typeface.create(args.getString("fontFamily"), args.getInt("fontStyle"));
    }//setArguments

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            ArrayList<ConChartView> conChartViews = new ArrayList<ConChartView>();
            seventeenArrayLists.add(conChartViews);
            double width_inch = this.maxInch * Math.pow(this.sizeRatio, y);
            ConChartView leftmost = new ConChartView(getActivity(), this.maxInch, -1.0, conChartViews, "×", this.nStrokes, this.typeface);
            leftmost.setMinimumHeight((int) ((this.maxInch + MARGIN_INCH) * l.getYdpi()));
            leftmost.setMinimumWidth((int) ((this.maxInch + MARGIN_INCH) * l.getXdpi()));
            TableRow table_row = new TableRow(getActivity());
            table_row.addView(leftmost);
            conChartViews.add(leftmost);
            for (int x = 0; x < this.nColumns; ++x) {
                TextView text_view = new TextView(getActivity());
                text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.RED);
                    }
                });
                final double intention = 255.0 - (255.0 * Math.pow(this.contrastRatio, x));
                ConChartView conChartView = new ConChartView(getActivity(), width_inch, intention, conChartViews, null, this.nStrokes, this.typeface);
                conChartView.setMinimumHeight((int) ((this.maxInch + MARGIN_INCH) * l.getYdpi()));
                conChartView.setMinimumWidth((int) ((this.maxInch + MARGIN_INCH) * l.getXdpi()));
                table_row.addView(conChartView);
                conChartViews.add(conChartView);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }///onSaveInstanceState

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);

        public float getXdpi();

        public float getYdpi();

        public int getWidthPixels();
    }//OnFragmentInteractionListener

    @Override
    public String toString() {
        StringWriter string_writer = new StringWriter();
        for (ArrayList<ConChartView> conChartViews : seventeenArrayLists) {
            for (ConChartView conChartView : conChartViews) {
                if (conChartView.isTouched()) {
                    string_writer.write(conChartView.toString());
                    string_writer.write(" ");
                }//if
            }//for
        }//for
        return string_writer.getBuffer().toString();
    }//toString
}//TconChartFragment
