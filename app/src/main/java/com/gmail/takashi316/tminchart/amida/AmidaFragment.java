package com.gmail.takashi316.tminchart.amida;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;
import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.fragment.NavigationDrawerFragment;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmidaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmidaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int lineWidth;
    private int lineContrast;

    LinearLayout linearLayoutAmidaStrips;
    Random random = new Random();
    Button buttonCloseAmida;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static AmidaFragment newInstance(int line_width, int line_contrast) {

        AmidaFragment fragment = new AmidaFragment();
        fragment.lineWidth = line_width;
        fragment.lineContrast = line_contrast;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AmidaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_amida, container, false);
        this.linearLayoutAmidaStrips = (LinearLayout) view.findViewById(R.id.linearLayoutAmidaStrips);
        this.buttonCloseAmida = (Button)view.findViewById(R.id.buttonCloseAmida);
        this.buttonCloseAmida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationDrawerFragment.NavigationDrawerCallbacks) getActivity()).onNavigationDrawerItemSelected(4);
            }
        });

        ArrayList<AmidaStripLayout> amida_strip_layouts = new ArrayList<AmidaStripLayout>();
        int[] left_ladders;
        int[] right_ladders = new int[3];
        for (int i = 0; i < 3; ++i) {
            right_ladders[i] = random.nextInt(18) + 1;
        }//for
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnFragmentInteractionListener) getActivity()).onEndPointClicked();
            }//onClick
        };
        AmidaStripLayout amida_strip_layout = new AmidaStripLayout(inflater.getContext(), "1", this.lineWidth, new int[]{}, right_ladders, 20, lineContrast);
        amida_strip_layouts.add(amida_strip_layout);
        amida_strip_layout.setOnEndPointClicked(l);
        this.linearLayoutAmidaStrips.addView(amida_strip_layout);
        for (int i = 2; i <= 10; ++i) {
            left_ladders = right_ladders;
            right_ladders = new int[3];
            for (int j = 0; j < 3; ++j) {
                right_ladders[j] = random.nextInt(18) + 1;
            }//for
            amida_strip_layout = new AmidaStripLayout(inflater.getContext(), "" + i, this.lineWidth, left_ladders, right_ladders, 20, lineContrast);
            amida_strip_layouts.add(amida_strip_layout);
            amida_strip_layout.setOnEndPointClicked(l);
            this.linearLayoutAmidaStrips.addView(amida_strip_layout);
        }//for
        amida_strip_layout = new AmidaStripLayout(inflater.getContext(), "6", lineWidth, right_ladders, new int[]{}, 20, lineContrast);
        amida_strip_layouts.add(amida_strip_layout);
        amida_strip_layout.setOnEndPointClicked(l);
        this.linearLayoutAmidaStrips.addView(amida_strip_layout);

        amida_strip_layouts.get(random.nextInt(6)).setStartPointRed();
        return view;
    }//onCreateView

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        public void onEndPointClicked();
    }

}
