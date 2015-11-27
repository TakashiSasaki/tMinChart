package com.gmail.takashi316.tminchart.stripe;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.gmail.takashi316.tminchart.R;

public class VerticalBlackOnWhite extends Activity
        implements StripeFragment.OnFragmentInteractionListener {

    final int COLUMNS = 50;
    final int ROWS = 45;
    final double COLOR_RATIO = Math.pow(0.01, 0.05);
    final double PIXEL_RATIO = Math.pow(0.01, 0.1);
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_view_test);

        Fragment fragment = new StripeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", ROWS);
        bundle.putInt("nTableColumns", COLUMNS);
        //bundle.putInt("nSteps", COLUMNS - 1);
        bundle.putIntegerArrayList("foregroundColorSequence", new ColorSequence(Color.BLACK, Color.WHITE, COLOR_RATIO, COLUMNS));
        bundle.putIntegerArrayList("backgroundColorSequence", ColorSequence.white(COLUMNS));
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(50, 1, ROWS - 1, true));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(50, 1, ROWS - 1, true));
        bundle.putInt("backgroundColor", Color.WHITE);
        bundle.putInt("frameWidth", 5);
        bundle.putInt("viewMargin", 5);
        fragment.setArguments(bundle);

        this.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }//onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stripe_view_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onStripeFinished() {
        this.finish();
    }
}
