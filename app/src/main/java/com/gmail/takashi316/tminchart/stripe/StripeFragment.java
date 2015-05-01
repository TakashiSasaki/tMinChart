package com.gmail.takashi316.tminchart.stripe;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
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

import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;

public class StripeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private Button buttonFinishStripe;
    private TableLayout tableLayoutStripe;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private int nTableRows;
    private int nTableColumns;
    private int[] backgroundColorSequence;
    private int[] foregroundColorSequence;
    private int[] backgroundStripeWidthSequence;
    private int[] foregroundStripeWidthSequence;

    public StripeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nTableRows = getArguments().getInt("nTableRows");
        this.nTableColumns = getArguments().getInt("nTableColumns");
        this.backgroundColorSequence = getArguments().getIntArray("backgroundColorSequence");
        this.foregroundColorSequence = getArguments().getIntArray("foregroundColorSequence");
        this.backgroundStripeWidthSequence = getArguments().getIntArray("backgroundStripeWidthSequence");
        this.foregroundStripeWidthSequence = getArguments().getIntArray("foregroundStripeWidthSequence");

        this.onFragmentInteractionListener = (OnFragmentInteractionListener) getActivity();
    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stripe, container, false);
        this.buttonFinishStripe = (Button) view.findViewById(R.id.buttonFinishStripe);
        this.tableLayoutStripe = (TableLayout) view.findViewById(R.id.tableLayoutStripe);

        this.buttonFinishStripe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onStripeFinished();
            }//onClick
        });

        final int TEXT_SIZE = 50;
        final Context context = this.getActivity();

        TextView text_view_left_top = new TextView(context);
        text_view_left_top.setTextSize(TEXT_SIZE);
        text_view_left_top.setText(" ");

        TableRow table_row_column_index = new TableRow(context);
        table_row_column_index.addView(text_view_left_top);

        for (int i = 1; i <= this.nTableColumns; ++i) {
            TextView text_view_column_index = new TextView(context);
            text_view_column_index.setTextSize(TEXT_SIZE);
            text_view_column_index.setText(new String(new byte[]{(byte) (i + 64)}));
            text_view_column_index.setGravity(Gravity.CENTER);
            table_row_column_index.addView(text_view_column_index);
        }//for

        tableLayoutStripe.addView(table_row_column_index);

        for (int r = 0; r < this.nTableRows; ++r) {
            TableRow table_row = new TableRow(context);
            TextView text_view_row_index = new TextView(context);
            text_view_row_index.setTextSize(TEXT_SIZE);
            text_view_row_index.setText(Integer.toString(r));
            text_view_row_index.setGravity(Gravity.CENTER);
            table_row.addView(text_view_row_index);
            ArrayList<StripeView> stripe_views = new ArrayList<StripeView>();
            for (int column = 1; column < this.nTableColumns; ++column) {
                StripeView stripe_view = new StripeView(context);
                stripe_view.setStripeViews(stripe_views);
                stripe_view.setGapInch(0.1f * (this.nTableRows - r));
                stripe_view.setWidthInch(1.5f);
                stripe_view.setIntensty(255.0 * Math.pow(0.80, column - 1));
                stripe_view.setVertical(true);
                stripe_view.setHorizontal(false);
                stripe_view.setPadding(30, 30, 30, 30);
                stripe_views.add(stripe_view);
                table_row.addView(stripe_view);
            }//for
            this.tableLayoutStripe.addView(table_row);
        }//for
        return view;
    }//onCreateView

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
        }
    }//onAttach

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }//onDetach

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);

        public void onStripeFinished();
    }

}
