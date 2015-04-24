package com.gmail.takashi316.tminchart.stripe;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.face.FaceRectangleFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StripeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StripeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StripeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button buttonFinishStripe;
    private TableLayout tableLayoutStripe;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StripeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StripeFragment newInstance(String param1, String param2) {
        StripeFragment fragment = new StripeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public StripeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        final int COLUMNS = 10;
        final int ROWS = 10;
        final Context context = this.getActivity();

        TextView text_view_left_top = new TextView(context);
        text_view_left_top.setTextSize(TEXT_SIZE);
        text_view_left_top.setText(" ");

        TableRow table_row_column_index = new TableRow(context);
        table_row_column_index.addView(text_view_left_top);

        for (int i = 1; i <= COLUMNS; ++i) {
            TextView text_view_column_index = new TextView(context);
            text_view_column_index.setTextSize(TEXT_SIZE);
            text_view_column_index.setText(new String(new byte[]{(byte) (i + 64)}));
            text_view_column_index.setGravity(Gravity.CENTER);
            table_row_column_index.addView(text_view_column_index);
        }//for

        tableLayoutStripe.addView(table_row_column_index);

        for (int r = 0; r < ROWS; ++r) {
            TableRow table_row = new TableRow(context);
            TextView text_view_row_index = new TextView(context);
            text_view_row_index.setTextSize(TEXT_SIZE);
            text_view_row_index.setText(Integer.toString(r));
            text_view_row_index.setGravity(Gravity.CENTER);
            table_row.addView(text_view_row_index);
            ArrayList<StripeView> stripe_views = new ArrayList<StripeView>();
            for (int column = 1; column < COLUMNS; ++column) {
                StripeView stripe_view = new StripeView(context, 0.1f, 1.0f, stripe_views);
                stripe_views.add(stripe_view);
            }
            this.tableLayoutStripe.addView(table_row);
        }//for
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

        public void onStripeFinished();
    }

}
