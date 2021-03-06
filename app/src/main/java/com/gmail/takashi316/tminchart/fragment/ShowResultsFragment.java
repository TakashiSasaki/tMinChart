package com.gmail.takashi316.tminchart.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.gmail.takashi316.tminchart.R;
import com.gmail.takashi316.tminchart.db.UserInfoSqliteOpenHelper;
import com.gmail.takashi316.tminchart.storage.DatabaseFile;
import com.gmail.takashi316.tminchart.db.ResultsSqliteOpenHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ShowResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SimpleAdapter simpleAdapter;

    private OnFragmentInteractionListener mListener;

    private Button buttonCopyDatabaseToSdCard;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowResults.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowResultsFragment newInstance(String param1, String param2) {
        ShowResultsFragment fragment = new ShowResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ShowResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }//if

        List<Map<String, String>> results = new ArrayList<Map<String, String>>();

        ResultsSqliteOpenHelper open_hehlper = new ResultsSqliteOpenHelper(getActivity());
        SQLiteDatabase database = open_hehlper.getReadableDatabase();
        Cursor cursor = database.query("ResultsTable", new String[]{
                        "_id", "date", "name", "tcon_chart_results", "tmin_chart_results",
                        "device", "dpi", "age", "sex", "affiliation",
                        "correction", "care", "careEx", "address", "fatigue",
                        "fatigueEx"},
                null, null, null, null, "_id DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Map<String, String> map = new HashMap<String, String>();
            map.put("name_and_datetime", cursor.getString(2) + " at " + cursor.getString(1));
            map.put("summary_results", "_id=" + Integer.toString(cursor.getInt(0)) + " tcon_chart_results=" + cursor.getString(3) +
                    " tmin_chart_results=" + cursor.getString(4) + " device=" + cursor.getString(5) + " dpi=" + cursor.getString(6) +
                            ", age=" + cursor.getInt(7) + ", sex=" +cursor.getString(8) + ", affiliation=" + cursor.getString(9) + ", correction=" + cursor.getString(10)+
                            ", care=" + cursor.getString(11) + ", careEx=" + cursor.getString(12) + ", address=" + cursor.getString(13) + ", fatigue=" +cursor.getString(14)+
                            ", fatigueEx="  + cursor.getString(15)
            );
            results.add(map);
            cursor.moveToNext();
        }//while
        cursor.close();

        simpleAdapter = new SimpleAdapter(getActivity(), results,
                android.R.layout.simple_list_item_2, new String[]{"name_and_datetime", "summary_results"}, new int[]{android.R.id.text1, android.R.id.text2 });

    }//onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_results, container, false);
        final ListView list_view = (ListView)view.findViewById(R.id.listViewResults);
        list_view.setAdapter(simpleAdapter);

        this.buttonCopyDatabaseToSdCard = (Button)view.findViewById(R.id.buttonCopyDatabaseToSdCard);
        this.buttonCopyDatabaseToSdCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            File f = new File("/storage/sdcard/hogehoge/a.txt");
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write("hello".getBytes());
                        } catch (Exception e){
                            Log.d(getClass().getSimpleName(), e.getMessage());
                        }

                        try {
                            DatabaseFile database_file = new DatabaseFile(getActivity(), UserInfoSqliteOpenHelper.DATABASE_FILE_NAME);
                            database_file.copyToExternalStorage();
                        } catch(FileNotFoundException e) {
                            Log.d(getClass().getSimpleName(), e.getMessage());
                            return;
                        } catch (IOException e){
                            Log.e(this.getClass().getSimpleName(), e.getMessage());
                        }
                    }//onClick
                }//OnClickListener
        );

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
