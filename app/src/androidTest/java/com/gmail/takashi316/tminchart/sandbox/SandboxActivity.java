package com.gmail.takashi316.tminchart.sandbox;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SandboxActivity extends Activity {
    ViewGroup content;
    LinearLayout linearLayout;
    Button button1, button2, button3;
    TextView textView;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.content = (ViewGroup) findViewById(android.R.id.content);

        this.linearLayout = new LinearLayout(this);
        this.content.addView(linearLayout);

        this.button1 = new Button(this);
        button1.setId(android.R.id.content + 11111111);
        button1.setTag(1);
        button1.setText("button 1");
        button1.setBackgroundColor(Color.RED);
        button1.setOnClickListener(onClickListener);
        this.linearLayout.addView(button1);

        this.button2 = new Button(this);
        button2.setId(android.R.id.content + 22222222);
        button2.setTag(2);
        button2.setText("button 2");
        button2.setBackgroundColor(Color.GREEN);
        button2.setOnClickListener(onClickListener);
        this.linearLayout.addView(button2);

        this.button3 = new Button(this);
        button3.setId(android.R.id.content + 33333333);
        button3.setTag(2);
        button3.setText("button 3");
        button3.setBackgroundColor(Color.BLUE);
        button3.setOnClickListener(onClickListener);
        this.linearLayout.addView(button3);

        textView = new TextView(this);
        textView.setId(android.R.id.content + 44444444);
        textView.setTag(4);
        this.linearLayout.addView(textView);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ++count;
            textView.setText("" + count);
        }
    };
}
