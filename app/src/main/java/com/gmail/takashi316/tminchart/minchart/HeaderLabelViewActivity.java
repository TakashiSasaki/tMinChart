package com.gmail.takashi316.tminchart.minchart;

import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

import com.gmail.takashi316.tminchart.R;

public class HeaderLabelViewActivity extends Activity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_header_label_view);
        this.linearLayout = (LinearLayout) this.findViewById(R.id.linearLayout);
        this.linearLayout.addView(new HeaderLabelView(this, "a"));
    }

}
