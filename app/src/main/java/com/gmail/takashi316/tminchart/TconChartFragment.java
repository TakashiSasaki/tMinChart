package com.gmail.takashi316.tminchart;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
    final String[] TCON_STRINGS  = {"講",	"謝", "績", "厳", "縮", "優", "覧", "曖", "臆",	"嚇",
        "轄", "環", "擬", "犠", "矯", "謹", "謙", "鍵", "購", "懇",
        "擦", "爵", "醜", "償", "礁", "繊", "鮮", "燥", "霜", "戴",
        "濯", "鍛", "聴", "謄", "瞳", "謎", "鍋", "頻", "闇", "翼", "療", "瞭", "齢"};
    
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TableLayout tableLayout;

    private OnFragmentInteractionListener mListener;

    private Random random = new Random();
    private String getTconString(){
        final int r = random.nextInt(TCON_STRINGS.length);
        return TCON_STRINGS[r];
    }//getTconString

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
        final int TEXT_SIZE = 70;
        TextView tv_tc  = new TextView(getActivity());
        tv_tc.setTextSize(TEXT_SIZE);
        tv_tc.setText("TC");
        tr_index.addView(tv_tc);
        final int COLUMNS = 20;
        final int ROWS = 10;
        for(int x = 1; x <= COLUMNS; ++x ){
            TextView text_view = new TextView(getActivity());
            text_view.setTextSize(TEXT_SIZE);
            text_view.setText(Integer.toString(x));
            tr_index.addView(text_view);
        }//for
        tableLayout.addView(tr_index);
        for(int y = 0; y<ROWS; ++y) {
            TableRow table_row = new TableRow(getActivity());
            TextView tv_index = new TextView(getActivity());
            tv_index.setTextSize(TEXT_SIZE);
            tv_index.setText(Integer.toString(y));
            table_row.addView(tv_index);
            for(int x=0; x<COLUMNS; ++x){
                TextView text_view = new TextView(getActivity());
                text_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.RED);
                    }
                });
                final double size = TEXT_SIZE / Math.pow(1.3, y);
                text_view.setTextSize((int)size);;
                text_view.setText(getTconString());
                final int intention = 255-(int)(255/Math.pow(1.3,x));
                text_view.setTextColor(Color.rgb(intention, intention,intention));
                table_row.addView(text_view);
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
