package com.gmail.takashi316.tminchart.minchart;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

import com.gmail.takashi316.tminchart.R;

public class HeaderLabelViewActivity extends Activity {

    private LinearLayout linearLayout;
    private HeaderLabelView headerLabelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_header_label_view);
        this.linearLayout = (LinearLayout) this.findViewById(R.id.linearLayout);
        this.headerLabelView = new HeaderLabelView(this, "a");
        this.headerLabelView.setWillNotDraw(false);
        this.headerLabelView.setWillNotCacheDrawing(false);
        this.linearLayout.addView(this.headerLabelView);
    }
}
