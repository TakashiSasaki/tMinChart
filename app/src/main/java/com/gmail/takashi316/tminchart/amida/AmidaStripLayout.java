package com.gmail.takashi316.tminchart.amida;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by sasaki on 2015/03/02.
 */
public class AmidaStripLayout extends LinearLayout {

    AmidaEndPointView amidaEndPointView;
    AmidaStartPointView amidaStartPointView;

    public AmidaStripLayout(Context context, String start_label, int line_width, int[] left_ladders, int[] right_ladders, int ladder_height, int line_contrast) {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setOrientation(VERTICAL);

        LayoutParams layout_params1 = new LayoutParams(200, 200);
        this.amidaStartPointView = new AmidaStartPointView(context, start_label);
        this.addView(amidaStartPointView, layout_params1);

        LayoutParams layout_params2 = new LayoutParams(200, 200);
        layout_params2.weight = 1;
        this.addView(new AmidaLadderView(context, line_width, left_ladders, right_ladders, ladder_height, line_contrast), layout_params2);
        this.amidaEndPointView = new AmidaEndPointView(context);
        this.addView(this.amidaEndPointView, layout_params1);
    }//AmidaStripLayout

    public void setOnEndPointClicked(OnClickListener on_click_listener) {
        this.amidaEndPointView.setOnClickListener(on_click_listener);
    }

    public void setStartPointRed() {
        amidaStartPointView.setBackgroundColor(Color.RED);
    }
}//AmidaStripLayout
