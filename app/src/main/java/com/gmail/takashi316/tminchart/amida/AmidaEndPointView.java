package com.gmail.takashi316.tminchart.amida;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by sasaki on 2015/03/02.
 */
public class AmidaEndPointView extends TextView {
    public AmidaEndPointView(Context context) {
        super(context);
        //this.setBackgroundColor(Color.GREEN);
        this.setGravity(Gravity.CENTER);
        this.setTextSize(80);
        this.setText("◆");
    }
}
