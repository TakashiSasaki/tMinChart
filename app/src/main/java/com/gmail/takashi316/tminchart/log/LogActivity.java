package com.gmail.takashi316.tminchart.log;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmail.takashi316.tminchart.R;

public class LogActivity extends Activity {
    Button buttonCreateDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log);
        this.buttonCreateDatabase = (Button) this.findViewById(R.id.buttonCreateDatabase);
        this.buttonCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
