package com.gmail.takashi316.tminchart;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Date;
import java.util.List;


public class TminChart extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TminChartFragment.OnFragmentInteractionListener,
        TconChartFragment.OnFragmentInteractionListener,
        DisplayPropertyFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener,
        ResultFragment.OnFragmentInteractionListener,
        SensorEventListener {

    private static final boolean USE_ACTION_BAR = false;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int decorViewWidth = -1;
    private int decorViewHeight = -1;

    private SensorManager sensorManager;
    private float lightSensorValue;
    private float accelerometerValueX;
    private float accelerometerValueY;
    private float accelerometerValueZ;

    private UserInfoFragment userInfoFragment;
    private TconChartFragment tconChartFragment;
    private TminChartFragment tminChartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmin_chart);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        try {
            PackageInfo package_info = getPackageManager().getPackageInfo("com.gmail.takashi316.tminchart", PackageManager.GET_META_DATA);
            final int versionCode = package_info.versionCode;
            final String versionName = package_info.versionName;
            mTitle = mTitle + " " + versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        maximizeBrightness();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }//onCreate

    private void maximizeBrightness() {
        WindowManager.LayoutParams layout_params = getWindow().getAttributes();
        layout_params.screenBrightness = 1.0f;
        getWindow().setAttributes(layout_params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if(position == 5){
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return;
        }//if
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, getFragment(position))
                .commit();
    }//onNavigationDrawerItemSelected

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section_result);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
        }//switch
    }//onSectionAttached

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen() && USE_ACTION_BAR) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.tmin_chart, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public String getTconChartResultString() {
        if(tconChartFragment != null){
            return tconChartFragment.toString();
        }else {
            return "tConChartは未測定です";
        }//if
    }//getTconChartResultString

    @Override
    public String getTminChartResultString() {
        if(tminChartFragment != null){
            return tminChartFragment.toString();
        } else {
            return "tMinChartは未測定です";
        }//if
    }//getTminChartResultString

    @Override
    public Date getDateTime() {
        return userInfoFragment.getDateTime();
    }

    @Override
    public String getName() {
        return userInfoFragment.getName();
    }

    @Override
    public int getAge() {
        return userInfoFragment.getAge();
    }

    @Override
    public String getSex() {
        return userInfoFragment.getSex();
    }

    @Override
    public String getAffiliation() {
        return userInfoFragment.getAffiliation();
    }

    @Override
    public String getCorrection() {
        return userInfoFragment.getCorrection();
    }

    @Override
    public String getFatigue() {
        return userInfoFragment.getFatigue();
    }

    @Override
    public String getAccelerometerString() {
        return "("+getAccelerometerX()+","+getAccelerometerY()+","+getAccelerometerZ()+")";
    }

    @Override
    public float getXdpi() {
        return getDisplayMetrics().xdpi;
    }

    @Override
    public float getYdpi() {
        return getDisplayMetrics().ydpi;
    }

    @Override
    public int getWidthPixels() {
        return getDisplayMetrics().widthPixels;
    }

    @Override
    public int getDecorViewWidth() {
        return this.decorViewWidth;
    }

    @Override
    public int getDecorViewHeight() {
        return this.decorViewHeight;
    }

    @Override
    public float getLightSensorValue() {
        return lightSensorValue;
    }

    @Override
    public float getAccelerometerX() {
        return accelerometerValueX;
    }

    @Override
    public float getAccelerometerY() {
        return accelerometerValueY;
    }

    @Override
    public float getAccelerometerZ() {
        return accelerometerValueZ;
    }

    DisplayMetrics displayMetrics;
    @Override
    public DisplayMetrics getDisplayMetrics() {
        if(displayMetrics == null){
            displayMetrics = new DisplayMetrics();
            getDisplay().getMetrics(displayMetrics);
        }//if
        return displayMetrics;
    }

    private Display display;
    @Override
    public Display getDisplay() {
        if(display == null){
            display = getWindowManager().getDefaultDisplay();
        }//if
        return display;
    } // getDisplay

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            this.lightSensorValue = event.values[0];
        }//if
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            this.accelerometerValueX = event.values[0];
            this.accelerometerValueY = event.values[1];
            this.accelerometerValueZ = event.values[2];
        }//if
    }//onSensorChanged

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Fragment getFragment(int section){
        switch(section){
            case 0:
                return userInfoFragment != null ? userInfoFragment : (userInfoFragment = new UserInfoFragment());
            case 1:
                return tconChartFragment != null ? tconChartFragment : (tconChartFragment = new TconChartFragment());
            case 2:
                return tminChartFragment != null ? tminChartFragment : (tminChartFragment = new TminChartFragment());
            case 3:
                return new ResultFragment();
            case 4:
                return new DisplayPropertyFragment();
            case 5:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return null;
            default:
                return null;
        }//switch
    }//getFragment
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment;
            switch (sectionNumber) {
                case 1:
                    fragment = new UserInfoFragment();
                    break;
                case 2:
                    fragment = new TconChartFragment();
                    break;
                case 3:
                    fragment = new TminChartFragment();
                    break;
                case 4:
                    fragment = new ResultFragment();
                    break;
                case 5:
                    fragment = new DisplayPropertyFragment();
                    break;
                default:
                    fragment = new PlaceholderFragment();
                    break;
            }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tmin_chart, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((TminChart) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }//PlaceHolderFragment

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) return;
        final View decor_view = this.getWindow().getDecorView();
        final ViewGroup view_group = (ViewGroup) decor_view;
        try {
            final LinearLayout linear_layout = (LinearLayout) view_group.getChildAt(0);
            this.decorViewHeight = decor_view.getHeight();
            this.decorViewWidth = decor_view.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //final Fragment display_property_fragment = this.getFragmentManager().findFragmentById(R.id.display_property);
        //display_property_fragment.onResume();
    }//onWindowFocusChanged

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> light_sensors = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
        if (light_sensors.size() > 0) {
            Sensor light_sensor = light_sensors.get(0);
            sensorManager.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_UI);
        }//if
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }//if
    }//onResume

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.display = null;
        this.displayMetrics = null;
    }
}//TminChart activity
