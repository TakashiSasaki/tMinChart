package com.gmail.takashi316.tminchart.amida;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by sasaki on 2015/03/02.
 */
public class AmidaStartPointView extends TextView {

    public AmidaStartPointView(Context context, String start_point) {
        super(context);
        this.setTextSize(80);
        this.setText(start_point);
        this.setGravity(Gravity.CENTER);
    }//StartPoint


}//StartPointView

