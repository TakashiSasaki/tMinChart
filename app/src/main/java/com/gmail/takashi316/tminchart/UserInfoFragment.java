package com.gmail.takashi316.tminchart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class UserInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextAffiliation;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private RadioButton radioButtonNoCorrection;
    private RadioButton radioButtonContactLens;
    private RadioButton radioButtonGlasses;
    private RadioButton radioButtonOtherCorrection;
    private RadioButton radioButtonBeforeDayShift;
    private RadioButton radioButtonAfterDayShift;
    private RadioButton radioButtonBeforeTwilgihtShift;
    private RadioButton radioButtonAfterTwilightShift;
    private RadioButton radioButtonBeforeNightShift;
    private RadioButton radioButtonAfterNightShift;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public UserInfoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        view.findViewById(R.id.buttonUserList).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CharSequence[] users = {"山田花子", "鈴木一郎", "吉田二郎"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("名前を選択してください");
                        builder.setItems(users,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        builder.show();
                    }//onClick
                }//OnClickListener
        );

        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextAge = (EditText) view.findViewById(R.id.editTextAge);
        editTextAffiliation = (EditText) view.findViewById(R.id.editTextAffiliation);
        radioButtonAfterDayShift = (RadioButton)view.findViewById(R.id.radioButtonAfterDayShift);
        radioButtonAfterNightShift = (RadioButton)view.findViewById(R.id.radioButtonAfterNightShift);
        radioButtonAfterTwilightShift = (RadioButton)view.findViewById(R.id.radioButtonAfterTwilightShift);
        radioButtonBeforeDayShift = (RadioButton)view.findViewById(R.id.radioButtonBeforeDayShift);
        radioButtonBeforeNightShift = (RadioButton)view.findViewById(R.id.radioButtonBeforeNightShift);
        radioButtonBeforeTwilgihtShift= (RadioButton)view.findViewById(R.id.radioButtonBeforeTwilighShift);
        radioButtonContactLens = (RadioButton)view.findViewById(R.id.radioButtonContactLens);
        radioButtonFemale = (RadioButton)view.findViewById(R.id.radioButtonFemale);
        radioButtonMale = (RadioButton)view.findViewById(R.id.radioButtonMale);
        radioButtonGlasses =(RadioButton)view.findViewById(R.id.radioButtonGlasses);
        radioButtonNoCorrection = (RadioButton)view.findViewById(R.id.radioButtonNoCorrection);
        radioButtonOtherCorrection = (RadioButton)view.findViewById(R.id.radioButtonOtherCorrection);

        view.findViewById(R.id.buttonSetUserInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().isEmpty()) return;
                final String name = editTextName.getText().toString();
                final Integer age = editTextAge.getText().toString().isEmpty() ? null : Integer.parseInt(editTextAge.getText().toString());
                final String sex = radioButtonMale.isChecked() ? "男性" : radioButtonFemale.isChecked() ? "女性" : "未回答";
                final String affiliation = editTextAffiliation.getText().toString();
                final String fatigue = radioButtonAfterDayShift.isChecked() ? radioButtonAfterDayShift.getText().toString():
                        radioButtonAfterNightShift.isChecked()? radioButtonAfterNightShift.getText().toString():
                                radioButtonAfterTwilightShift.isChecked() ? radioButtonAfterTwilightShift.getText().toString():
                                        radioButtonBeforeDayShift.isChecked()? radioButtonBeforeDayShift.getText().toString():
                                                radioButtonBeforeNightShift.isChecked()?radioButtonBeforeNightShift.getText().toString():
                                                        radioButtonBeforeTwilgihtShift.isChecked()?radioButtonBeforeTwilgihtShift.getText().toString() : null;
                final String correction = radioButtonNoCorrection.isChecked()? radioButtonNoCorrection.getText().toString():
                        radioButtonOtherCorrection.isChecked()?radioButtonOtherCorrection.getText().toString():
                                radioButtonContactLens.isChecked()?radioButtonContactLens.getText().toString():
                                        radioButtonGlasses.isChecked()?radioButtonGlasses.getText().toString() : null;

                SQLiteOpenHelper  sqlite_open_helper = new UserInfoSqliteOpenHelper(getActivity());
                SQLiteDatabase writable_database = sqlite_open_helper.getWritableDatabase();
                Cursor cursor = writable_database.query("UserInfo",
                        new String[]{"name", "age", "sex", "affiliation", "correction", "fatigue"},
                        null, null, null, null, null, null);
                if(cursor.getCount() > 0){
                    writable_database.delete("UserInfo", "name = ?", new String[]{name});
                }//if
                cursor.close();
                ContentValues content_values = new ContentValues();
                content_values.put("name", name);
                content_values.put("age", age);
                content_values.put("sex", sex);
                content_values.put("affiliation", affiliation);
                content_values.put("correction", correction);
                content_values.put("fatigue", fatigue);
                writable_database.insert("UserInfo", null, content_values);
            }//onClick
        });
        return view;
    }

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
