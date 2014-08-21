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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gmail.takashi316.tminchart.fragment.DisplayPropertyFragment;
import com.gmail.takashi316.tminchart.fragment.NavigationDrawerFragment;
import com.gmail.takashi316.tminchart.fragment.ResultFragment;
import com.gmail.takashi316.tminchart.fragment.ShowResultsFragment;
import com.gmail.takashi316.tminchart.fragment.TconChartFragment;
import com.gmail.takashi316.tminchart.fragment.TminChartFragment;
import com.gmail.takashi316.tminchart.fragment.UploadFragment;
import com.gmail.takashi316.tminchart.fragment.UserInfoFragment;

import java.util.List;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TminChartFragment.OnFragmentInteractionListener,
        TconChartFragment.OnFragmentInteractionListener,
        DisplayPropertyFragment.OnFragmentInteractionListener,
        UserInfoFragment.OnFragmentInteractionListener,
        ResultFragment.OnFragmentInteractionListener,
        ShowResultsFragment.OnFragmentInteractionListener,
        UploadFragment.OnFragmentInteractionListener,
        SensorEventListener {

    private static Class[] fragmentClasses = {UserInfoFragment.class, TminChartFragment.class,
    TconChartFragment.class, DisplayPropertyFragment.class, ResultFragment.class, ShowResultsFragment.class};

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
        this.mTitle = getTitle();
        try {
            PackageInfo package_info = getPackageManager().getPackageInfo("com.gmail.takashi316.tminchart", PackageManager.GET_META_DATA);
            final int versionCode = package_info.versionCode;
            final String versionName = package_info.versionName;
            this.mTitle = mTitle + " " + versionName;
            setTitle(this.mTitle);
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
    public String[] getNavigationDrawerTitles() {
        return new String[] {
                getString(R.string.UserInfoFragment),
                getString(R.string.TconChartFragment),
                getString(R.string.TminChartFragment),
                getString(R.string.ResultFragment),
                getString(R.string.ShowResultsFragment),
                getString(R.string.UploadFragment),
                getString(R.string.DisplayPropertyFragment),
                getString(R.string.SettingsActivity)
        };
    }//getNavigationDrawerTitles

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        mTitle = getNavigationDrawerTitles()[position];
        Fragment fragment = null;
        switch(position){
            case 1:
                fragment =  tconChartFragment != null ? tconChartFragment : (tconChartFragment = new TconChartFragment());
                break;
            case 2:
                fragment =  tminChartFragment != null ? tminChartFragment : (tminChartFragment = new TminChartFragment());
                break;
            case 3:
                fragment =  new ResultFragment();
                break;
            case 4:
                fragment =  new ShowResultsFragment();
                break;
            case 5:
                fragment = new UploadFragment();
                break;
            case 6:
                fragment =  new DisplayPropertyFragment();
                break;
            case 7:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return;
            case 0:
                fragment = userInfoFragment != null ? userInfoFragment : (userInfoFragment = new UserInfoFragment());
        }//switch
        if(fragment == null) return;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }//onNavigationDrawerItemSelected

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
    public UsersTable getUsersTable() {
        return userInfoFragment.usersTable;
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
