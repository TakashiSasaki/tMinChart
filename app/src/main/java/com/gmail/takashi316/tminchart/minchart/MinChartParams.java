package com.gmail.takashi316.tminchart.minchart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MinChartParams {
    public double tminChartMaxGapInch;
    //public double tminChartGapInch;
    public double tminChartRatio;
    public int tminChartCount;

    public MinChartParams() {
        this.tminChartCount = 200;
        this.tminChartMaxGapInch = 0.3;
        this.tminChartRatio = Math.pow(0.1, 0.01);
    }

    public void readSharedPreferences(Context context) {
        this.readSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public void readSharedPreferences(SharedPreferences shared_preferences) {
        try {
            this.tminChartMaxGapInch = Double.parseDouble(shared_preferences.getString("tmin_chart_max_gap_inch", Double.toString(this.tminChartMaxGapInch)));
            this.tminChartRatio = Double.parseDouble(shared_preferences.getString("tmin_chart_ratio", Double.toString(this.tminChartRatio)));
            this.tminChartCount = Integer.parseInt(shared_preferences.getString("tmin_chart_count", Integer.toString(this.tminChartCount)));
        } catch (Exception e) {
            e.printStackTrace();
        }//try
    }
}