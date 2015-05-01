package com.gmail.takashi316.tminchart.stripe;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.gmail.takashi316.tminchart.R;

public class StripeViewTestActivity extends Activity
        implements StripeFragment.OnFragmentInteractionListener {

    final int COLUMNS = 4;
    final int ROWS = 2;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_view_test);
        this.viewGroup = (ViewGroup) this.findViewById(R.id.container);

        Fragment fragment = new StripeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("nTableRows", ROWS);
        bundle.putInt("nTableColumns", COLUMNS);
        bundle.putInt("nSteps", COLUMNS - 1);
        bundle.putIntegerArrayList("foregroundColorSequence", new ColorSequence(Color.BLACK, Color.WHITE, COLUMNS - 1, false));
        bundle.putIntegerArrayList("backgroundColorSequence", new ColorSequence(Color.WHITE, Color.BLACK, COLUMNS - 1, false));
        bundle.putIntegerArrayList("foregroundWidthSequence", new PixelSequence(100, 1, ROWS - 1, false));
        bundle.putIntegerArrayList("backgroundWidthSequence", new PixelSequence(100, 1, ROWS - 1, false));
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

    }
}
