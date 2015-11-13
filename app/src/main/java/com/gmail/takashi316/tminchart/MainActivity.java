package com.gmail.takashi316.tminchart;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gmail.takashi316.tminchart.amida.AmidaFragment;
import com.gmail.takashi316.tminchart.db.UsersTable;
import com.gmail.takashi316.tminchart.fragment.DisplayPropertyFragment;
import com.gmail.takashi316.tminchart.fragment.NavigationDrawerFragment;
import com.gmail.takashi316.tminchart.fragment.ResultFragment;
import com.gmail.takashi316.tminchart.fragment.SettingsFragment;
import com.gmail.takashi316.tminchart.fragment.ShowResultsFragment;
import com.gmail.takashi316.tminchart.fragment.TconChartFragment;
import com.gmail.takashi316.tminchart.fragment.TminChartFragment;
import com.gmail.takashi316.tminchart.fragment.UploadFragment;
import com.gmail.takashi316.tminchart.fragment.UserInfoFragment;
import com.gmail.takashi316.tminchart.stripe.StripeFragment;
import com.gmail.takashi316.tminchart.stripe.StripeFragmentFactory;

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
        SettingsFragment.OnFragmentInteractionListener,
        AmidaFragment.OnFragmentInteractionListener,
        StripeFragment.OnFragmentInteractionListener,
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
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_tmin_chart);
        this.getActionBar().hide();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        this.mTitle = getTitle();
        try {
            PackageInfo package_info = getPackageManager().getPackageInfo("com.gmail.takashi316.tminchart_beta", PackageManager.GET_META_DATA);
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
        return new String[]{
                getString(R.string.UserInfoFragment),
                getString(R.string.TconChartFragment),
                getString(R.string.TminChartFragment),
                "Amida",
                "Stripe (白地に黒、コントラスト低→高)",
                "Stripe (白地に黒、コントラスト高→低)",
                "Stripe (黒地に白、コントラスト低→高)",
                "Stripe (黒地に白、コントラスト高→低)",
                "Stripe (黒地に赤、コントラスト高→低)",
                getString(R.string.ResultFragment),
                getString(R.string.ShowResultsFragment),
                getString(R.string.UploadFragment),
                getString(R.string.DisplayPropertyFragment),
                getString(R.string.SettingsFragment)
        };
    }//getNavigationDrawerTitles

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        mTitle = getNavigationDrawerTitles()[position];
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        final int ROWS = 15;
        final int COLUMNS = 26;
        switch (position) {
            case 1:
                Typeface font_family = Typeface.DEFAULT;
                String font_family_string = preferences.getString("fontFamily", "DEFAULT");
                if (font_family_string.equals("DEFAULT")) font_family = Typeface.DEFAULT;
                else if (font_family_string.equals("DEFAULT_BOLD"))
                    font_family = Typeface.DEFAULT_BOLD;
                else if (font_family_string.equals("MONOSPACE"))
                    font_family = Typeface.MONOSPACE;
                else if (font_family_string.equals("SANS_SERIF"))
                    font_family = Typeface.SANS_SERIF;
                else if (font_family_string.equals("SERIF"))
                    font_family = Typeface.SERIF;
                int font_style = 0;
                String font_style_string = preferences.getString("fontStyle", "NORMAL");
                if (font_style_string.equals("NORMAL")) font_style = 0;
                else if (font_style_string.equals("BOLD")) font_style = 1;
                else if (font_style_string.equals("ITALIC")) font_style = 2;
                else if (font_style_string.equals("BOLD_ITALIC")) font_style = 3;
                fragment = TconChartFragment.newInstance(Integer.parseInt(preferences.getString("nStrokes", "17")),
                        Integer.parseInt(preferences.getString("nRows", "20")),
                        Integer.parseInt(preferences.getString("nColumns", "30")),
                        Float.parseFloat(preferences.getString("maxInch", "0.5")),
                        Float.parseFloat(preferences.getString("sizeRatio", "" + (float) Math.pow(0.1, 0.1))),
                        Float.parseFloat(preferences.getString("contrastRatio", "" + (float) Math.pow(0.1, 0.1))),
                        Typeface.create(font_family, font_style));
                break;
            case 2:
                fragment = tminChartFragment != null ? tminChartFragment : (tminChartFragment = new TminChartFragment());
                break;
            case 3:
                fragment = AmidaFragment.newInstance(30, 255);
                break;
            case 4:
                fragment = StripeFragmentFactory.createWhiteBackgroundToBlackStripe(this);
                break;
            case 5:
                fragment = StripeFragmentFactory.createWhiteBackground(this);
                break;
            case 6:
                fragment = StripeFragmentFactory.grayBackgroundToBlack(this);
                break;
            case 7:
                fragment = StripeFragmentFactory.grayBackgroundToWhite(this);
                break;
            case 8:
                fragment = StripeFragmentFactory.redBackgroundFromBlack(this);
                break;
            case 9:
                fragment = new ResultFragment();
                break;
            case 10:
                fragment = new ShowResultsFragment();
                break;
            case 11:
                fragment = new UploadFragment();
                break;
            case 12:
                fragment = new DisplayPropertyFragment();
                break;
            case 13:
                fragment = SettingsFragment.newInstance();
                break;
            case 14:
                this.preferences.edit().clear().commit();
                //PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_tmin_chart, true);
                return;
            //Intent intent = new Intent(this, SettingsActivity.class);
            //this.startActivity(intent);
            //return;
            case 0:
                fragment = userInfoFragment != null ? userInfoFragment : (userInfoFragment = new UserInfoFragment());
        }//switch
        if (fragment == null) return;
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
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    @Override
    public void onFragmentInteraction(Uri uri) {
    }//onFragmentInteraction

    @Override
    public void onStripeFinished() {
        this.onNavigationDrawerItemSelected(5);
    }

    @Override
    public void onEndPointClicked() {

    }

    @Override
    public String getTconChartResultString() {
        if (tconChartFragment != null) {
            return tconChartFragment.toString();
        } else {
            return "tConChartは未測定です";
        }//if
    }//getTconChartResultString

    @Override
    public String getTminChartResultString() {
        if (tminChartFragment != null) {
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
        return "(" + getAccelerometerX() + "," + getAccelerometerY() + "," + getAccelerometerZ() + ")";
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

    /* TODO: should be replaced with DisplayDpi class */
    @Override
    public DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            getDisplay().getMetrics(displayMetrics);
        }//if
        return displayMetrics;
    }

    private Display display;

    @Override
    public Display getDisplay() {
        if (display == null) {
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
            // Top view group is ActionBarOverlayLayout on some devices
            //final LinearLayout linear_layout = (LinearLayout) view_group.getChildAt(0);
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
